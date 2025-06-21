package com.swpteam.smokingcessation.feature.service.interfaces.notification;

import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.domain.dto.message.MessageRequest;
import com.swpteam.smokingcessation.domain.dto.message.MessageResponse;
import org.springframework.data.domain.Page;

public interface MessageService {

    public Page<MessageResponse> getMessagePage(PageableRequest request);

    public MessageResponse getById(String id);

    public MessageResponse createMessage(MessageRequest request);

    public MessageResponse updateMessage(String id, MessageRequest request);

    public void softDeleteMessageById(String id);
}
