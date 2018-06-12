package com.robpercival.demoapp.rest.service.impl;

import com.robpercival.demoapp.rest.CommentsAPI;
import com.robpercival.demoapp.rest.dto.CommentDto;
import com.robpercival.demoapp.rest.service.CommentService;
import com.robpercival.demoapp.rest.service.ServiceCallback;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentServiceImpl extends BaseServiceImpl implements CommentService {

    private CommentsAPI commentsAPI;

    private static CommentService instance;

    private CommentServiceImpl() {
        super();
        buildHttpClient();
    }

    public static CommentService getInstance() {
        if (instance == null) {
            instance = new CommentServiceImpl();
        }
        return instance;
    }

    private void buildHttpClient() {
        commentsAPI = retrofit.create(CommentsAPI.class);
    }

    @Override
    public void getAllCommentsForRestaurant(long restaurantId, final ServiceCallback<List<CommentDto>> presenterCallback) {

        Call<List<CommentDto>> apiCall = commentsAPI.getCommentsForRestaurant(restaurantId);
        apiCall.enqueue(new Callback<List<CommentDto>>() {

            @Override
            public void onResponse(Call<List<CommentDto>> call, Response<List<CommentDto>> response) {
                List<CommentDto> body = response.body();
                presenterCallback.onSuccess(body);
            }

            @Override
            public void onFailure(Call<List<CommentDto>> call, Throwable t) {
            }
        });
    }

    @Override
    public void addComment(CommentDto comm, final ServiceCallback<CommentDto> presenterCallback) {

        Call<CommentDto> apiCall = commentsAPI.addComment(comm);
        apiCall.enqueue(new Callback<CommentDto>() {

            @Override
            public void onResponse(Call<CommentDto> call, Response<CommentDto> response) {
                CommentDto body = response.body();
                presenterCallback.onSuccess(body);
            }

            @Override
            public void onFailure(Call<CommentDto> call, Throwable t) {
            }
        });
    }
}
