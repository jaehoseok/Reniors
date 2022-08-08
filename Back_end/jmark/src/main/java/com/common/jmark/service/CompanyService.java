package com.common.jmark.service;

import com.common.jmark.common.config.security.util.JwtUtil;
import com.common.jmark.common.exception.DuplicateException;
import com.common.jmark.common.exception.NotAuthException;
import com.common.jmark.common.exception.NotFoundException;
import com.common.jmark.common.exception.NotMatchException;
import com.common.jmark.domain.entity.Apply;
import com.common.jmark.domain.entity.Company;
import com.common.jmark.domain.entity.JobOpening;
import com.common.jmark.domain.entity.category.Gugun;
import com.common.jmark.domain.entity.category.JobChildCategory;
import com.common.jmark.domain.repository.ApplyRepository;
import com.common.jmark.domain.repository.CompanyRepository;
import com.common.jmark.domain.repository.JobOpeningRepository;
import com.common.jmark.domain.repository.category.GugunRepository;
import com.common.jmark.domain.repository.category.JobChildCategoryRepository;
import com.common.jmark.dto.Apply.ApplyResponse;
import com.common.jmark.dto.Apply.ApplyUpdateRequest;
import com.common.jmark.dto.Company.CompanyCreateRequest;
import com.common.jmark.dto.Company.CompanyResponse;
import com.common.jmark.dto.Company.CompanyUpdateRequest;
import com.common.jmark.dto.Company.CompanyLoginRequest;
import com.common.jmark.dto.JobOpening.*;
import com.common.jmark.dto.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.common.jmark.common.exception.NotAuthException.COMPANY_NO_AUTH;
import static com.common.jmark.common.exception.NotFoundException.USER_NOT_FOUND;
import static com.common.jmark.common.exception.NotMatchException.PASSWORD_NOT_MATCH;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final JobOpeningRepository jobOpeningRepository;
    private final CompanyRepository companyRepository;
    private final GugunRepository gugunRepository;
    private final JobChildCategoryRepository jobChildCategoryRepository;
    private final ApplyRepository applyRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    //회사 회원가입
    @Transactional
    public Long postCompany(CompanyCreateRequest request){
        if (companyRepository.findByCompanyNum(request.getCompanyNum()).isPresent()) {
            throw new DuplicateException(String.format("%s은 이미 가입된 기업입니다.", request.getName()));
        } else {
        Company company = Company.create(
                request.getName(),
                request.getCompanyAppId(),
                passwordEncoder.encode(request.getCompanyAppPwd()),
                request.getEstablishedAt(),
                request.getCompanyUrl(),
                request.getAddress(),
                request.getCompanyNum(),
                request.getCompanyPhone(),
                request.getRepresentative(),
                request.getRepresentativePhone(),
                request.getTypeCompany()
        );
        return companyRepository.save(company).getId();
        }
    }

    @Transactional
    public String loginCompany(CompanyLoginRequest request){
        Optional<Company> optionalCompany = companyRepository.findByCompanyAppId(request.getCompanyAppId());
        if (!optionalCompany.isPresent()){
            throw new NotFoundException(USER_NOT_FOUND);
        }else {
            if (passwordEncoder.matches(request.getCompanyAppPwd(), optionalCompany.get().getCompanyAppPwd())){
                return jwtUtil.createToken(optionalCompany.get().getId(), "company");
            }else {
                throw new NotMatchException(PASSWORD_NOT_MATCH);
            }
        }
    }

    //회사 상세정보
    @Transactional
    public CompanyResponse getCompany(Company company){
        Company company1 = companyRepository.findById(company.getId()).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        CompanyResponse response = CompanyResponse.response(company1);
        return response;
    }

    //회사 정보수정
    @Transactional
    public void updateCompany(Company company, CompanyUpdateRequest request){
        company.update(request);
    }

    //회사 탈퇴
    @Transactional
    public void deleteCompany(Company company){
        companyRepository.deleteById(company.getId());
    }

    //회사 공고 등록
    @Transactional
    public Long postJobOpening(Company company, JobOpeningCreateRequest jobOpeningCreateRequest){
        Gugun gugun = gugunRepository.findById(jobOpeningCreateRequest.getGugunId()).orElseThrow(() -> new NotFoundException("not found gugun"));
        JobChildCategory jobChildCategory = jobChildCategoryRepository.findById(jobOpeningCreateRequest.getJobChildCategoryId()).orElseThrow(() -> new NotFoundException("not found jcc"));
        JobOpening jobOpening = new JobOpening(jobOpeningCreateRequest,company,gugun,jobChildCategory);
        Long id = jobOpeningRepository.save(jobOpening).getId();
        return id;
    }

    //회사 공고 목록
    @Transactional
    public List<JobOpeningCompanyResponse> getJobOpeningList(Company company){

            List<JobOpening> jobOpeningList = jobOpeningRepository.findByCompany(company);


            List<JobOpeningCompanyResponse> jobOpeningResponses = jobOpeningList.stream().map(j->JobOpeningCompanyResponse.response(
                    j
            )).collect(Collectors.toList());
            return jobOpeningResponses;
    }

    //회사 공고 상세조회
    @Transactional
    public JobOpeningDetailResponse getJobOpening(Company company, Long jobOpeningId){
        if (company.getId() != jobOpeningRepository.findById(jobOpeningId).get().getCompany().getId())
            throw new NotAuthException(COMPANY_NO_AUTH);
        JobOpening jobOpening = jobOpeningRepository.findById(jobOpeningId).orElseThrow(() -> new NotFoundException("not found jobOpening"));

            //연결된 엔티티 매핑
//            GugunResponse gugunResponse = GugunResponse.response(jobOpening1.getGugun());
//            JobChildCategoryResponse jobChildCategoryResponse = JobChildCategoryResponse.response(jobOpening1.getJobChildCategory());
//            CompanyDto companyDto = new CompanyDto(jobOpening1.getCompany());
        JobOpeningDetailResponse response = JobOpeningDetailResponse.response(jobOpening);
        //리턴할 Dto 세팅
//            JobOpeningDto jobOpeningDto = new JobOpeningDto(jobOpening1,companyDto,gugunResponse,jobChildCategoryResponse);
//            jobOpeningDto.setLinkEntity(companyDto,gugunResponse,jobChildCategoryResponse);
            return response;
    }

    //회사 공고 수정
    @Transactional
    public void updateJobOpening(Company company, Long jobOpeningId, JobOpeningUpdateRequest jobOpeningUpdateRequest){
        if (company.getId() != jobOpeningRepository.findById(jobOpeningId).get().getCompany().getId())
            throw new NotAuthException(COMPANY_NO_AUTH);
        Gugun gugun = gugunRepository.findById(jobOpeningUpdateRequest.getGugunId()).orElseThrow(() -> new NotFoundException("not found gugun"));
        JobChildCategory jobChildCategory = jobChildCategoryRepository.findById(jobOpeningUpdateRequest.getJobChildCategoryId()).orElseThrow(() -> new NotFoundException("not found jcc"));
        JobOpening jobOpening = jobOpeningRepository.findById(jobOpeningId).orElseThrow(() -> new NotFoundException("not found jobOpening"));
        jobOpening.update(jobOpeningUpdateRequest,gugun,jobChildCategory);
    }

    //회사 공고 삭제
    @Transactional
    public void deleteJobOpening(Company company, Long jobOpeningId){
        if (company.getId() != jobOpeningRepository.findById(jobOpeningId).get().getCompany().getId())
            throw new NotAuthException(COMPANY_NO_AUTH);
        jobOpeningRepository.deleteById(jobOpeningId);
    }

    //회사 공고 끝내기
    @Transactional
    public void finishJobOpening(Company company, Long jobOpeningId){
        if (company.getId() != jobOpeningRepository.findById(jobOpeningId).get().getCompany().getId())
            throw new NotAuthException(COMPANY_NO_AUTH);

        JobOpening jobOpening = jobOpeningRepository.findById(jobOpeningId).orElseThrow(() -> new NotFoundException("not found jobopening"));
        jobOpening.finish();
    }

    //회사 공고 지원자 목록
    @Transactional
    public List<ApplyResponse> getappliyList(Company company, Long jobOpeningId){
        if (company.getId() != jobOpeningRepository.findById(jobOpeningId).get().getCompany().getId())
            throw new NotAuthException(COMPANY_NO_AUTH);
        JobOpening jobOpening = jobOpeningRepository.findById(jobOpeningId).orElseThrow(() -> new NotFoundException("not found Apply"));
        List<Apply> applyList = applyRepository.findByJobOpening(jobOpening);
        List<ApplyResponse> applyResponseList = applyList.stream().map(a->ApplyResponse.response(
                a
        )).collect(Collectors.toList());
        return  applyResponseList;
    }

    //회사 공고 지원자 상세목록
    @Transactional
    public UserResponse getapplicant(Company company, Long applyId){
        if(company.getId() != applyRepository.findById(applyId).get().getJobOpening().getCompany().getId())
            throw new NotAuthException(COMPANY_NO_AUTH);
        Apply apply = applyRepository.findById(applyId).orElseThrow(() -> new NotFoundException("not found Apply"));
        UserResponse userResponse = UserResponse.response(apply.getUser());

        return userResponse;
    }

    //회사 공고 지원자 상태수정
    @Transactional
    public void updateapply(Company company, Long applyId, ApplyUpdateRequest applyUpdateRequest){
        if(company.getId() != applyRepository.findById(applyId).get().getJobOpening().getCompany().getId())
            throw new NotAuthException(COMPANY_NO_AUTH);
        Apply apply = applyRepository.findById(applyId).orElseThrow(() -> new NotFoundException("not found Apply"));
        apply.update(applyUpdateRequest,apply.getUser(), apply.getJobOpening());
    }
}
