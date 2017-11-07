package com.reframe.lapp.network;

import com.reframe.lapp.models.BaseResponse;
import com.reframe.lapp.models.BaseResponseList;
import com.reframe.lapp.models.FeedbackQuery;
import com.reframe.lapp.models.ProfessorEvaluation;
import com.reframe.lapp.models.ProfessorScore;
import com.reframe.lapp.models.SuccessResponse;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import rx.Single;

/**
 * Created by Aldo on 01-11-2016.
 */


public interface LappInterface {

    @GET("api/users/me")
    Observable<BaseResponse> getMe(@Header("Authorization") String token);

    @GET("api/levels/")
    Observable<BaseResponseList> getLevels(@Header("Authorization") String token);

    @GET("api/levels/{level}?populate=tutorials")
    Observable<BaseResponseList> getGroupsByLevel(@Header("Authorization") String token, @Path("level") String level);

    @GET("api/students/evaluations")
    Observable<BaseResponseList> getEvaluationTimeline(@Header("Authorization") String token);

    @GET("api/professors/list-video-feed-back/{level}")
    Observable<BaseResponseList> getProfessorVideoFeedbackList(@Header("Authorization") String token,  @Path("level") String level);

    @GET("api/professors/evaluations")
    Observable<BaseResponseList> getProfessorEvaluationTimelineFiltered(@Header("Authorization") String token,  @Query("username") String username,  @Query("level") String level);

    @GET("api/professors/evaluations")
    Observable<BaseResponseList> getProfessorEvaluationTimeline(@Header("Authorization") String token);

    @GET("api/students/my-evolution")
    Observable<BaseResponseList> getEvolutionInformation(@Header("Authorization") String token,  @Query("level") String level);

    @GET("api/students/{studentId}/evolution")
    Observable<BaseResponseList> getStudentEvolutionInformation(@Header("Authorization") String token,  @Query("level") String level, @Path("studentId") String studentId);

    @GET("api/professor/my-students")
    Observable<BaseResponseList> getProfessorStudents(@Header("Authorization") String token,  @Query("username") String username, @Query("page") int page);

    @GET("api/media/feed-back")
    Observable<BaseResponseList> getVideoFeedbackList(@Header("Authorization") String token, @Query("exerciseId") String exerciseId);

    @FormUrlEncoded
    @POST("login")
    Observable<BaseResponse> login(@Field("email") String email, @Field("password") String password);

    @POST("api/evaluations/evaluate")
    Observable<BaseResponse> evaluateAsProfessor(@Header("Authorization") String token, @Body ProfessorEvaluation evaluation);

    @POST("api/evaluations/professor")
    Observable<BaseResponse> evaluateProfessor(@Header("Authorization") String token, @Body ProfessorScore score);

    @PUT("api/evaluations/{evaluationId}/add-feed-back")
    Observable<BaseResponse> addFeedBack(@Header("Authorization") String token, @Path("evaluationId") String evaluationId, @Body FeedbackQuery feedbackQuery);

    @FormUrlEncoded
    @POST("api/media/create")
    Observable<BaseResponse> createMedia(@Field("url") String url, @Field("typeMedia") String typeMedia);

    //Image upload
    @Multipart
    @POST("api/media")
    Observable<BaseResponse> uploadMedia(@Part MultipartBody.Part media);

    //Logout
    @PUT("api/users/delete-device-token")
    Single<SuccessResponse> doLogout(@Field("device") String device, @Field("token") String deviceToken);

    //Change Password
    @FormUrlEncoded
    @PUT("api/users/change-password")
    Single<BaseResponse> changePassword(@Header("Authorization") String token, @Field("oldPassword") String oldPassword, @Field("newPassword") String newPassword);


    //Change Email
    @FormUrlEncoded
    @PUT("api/users")
    Single<BaseResponse> changeEmail(@Header("Authorization") String token, @Field("email") String email);


    //Save device token
    @FormUrlEncoded
    @PUT("api/users/set-token")
    Observable<BaseResponse> setDeviceToken(@Header("Authorization") String code, @Field("token") String token, @Field("device") String device);


}