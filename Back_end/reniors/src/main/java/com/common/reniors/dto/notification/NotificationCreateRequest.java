package com.common.reniors.dto.notification;

import com.common.reniors.domain.entity.Type.JobOpeningProcess;
import com.common.reniors.domain.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationCreateRequest {
    private Long userId;

    private Long applyId;
}
