package com.robpercival.demoapp.rest.service;

import com.robpercival.demoapp.rest.dto.CommentDto;

import java.util.List;

public interface CommentService {

    void getAllCommentsForRestaurant(long restaurantId, final ServiceCallback<List<CommentDto>> presenterCallback);

    void addComment(CommentDto comm,  final ServiceCallback<CommentDto> presenterCallback);
}
