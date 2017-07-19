package com.reframe.lapp.network;

import android.util.Log;
import android.webkit.MimeTypeMap;

import com.google.gson.Gson;
import com.pixplicity.easyprefs.library.Prefs;
import com.reframe.lapp.constants.Constants;
import com.reframe.lapp.models.AccessToken;
import com.reframe.lapp.models.AudioFeedback;
import com.reframe.lapp.models.BaseResponse;
import com.reframe.lapp.models.BaseResponseList;
import com.reframe.lapp.models.Evaluation;
import com.reframe.lapp.models.EvolutionData;
import com.reframe.lapp.models.Group;
import com.reframe.lapp.models.ImageUpload;
import com.reframe.lapp.models.Level;
import com.reframe.lapp.models.Media;
import com.reframe.lapp.models.ProfessorEvaluation;
import com.reframe.lapp.models.Student;
import com.reframe.lapp.models.SuccessResponse;
import com.reframe.lapp.models.User;
import com.reframe.lapp.models.VideoFeedback;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Single;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Aldo on 01-11-2016.
 */

public class LappService {
    private static LappInterface apiInterface;

    public static LappInterface getClient() {
        if(apiInterface == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            OkHttpClient httpClient = new OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(interceptor).build();

            Retrofit client = new Retrofit.Builder()
                    .baseUrl(Constants.DEV_API_URL)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            apiInterface = client.create(LappInterface.class);
        }
        return apiInterface;
    }

    public static Observable<User> getMe() {
        String token = Prefs.getString(Constants.LAPP_TOKEN, "");
        return getClient().getMe(token).map(baseResponse -> convertFromBase(baseResponse, User.class))
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<List<Level>> getLevels() {
        String token = Prefs.getString(Constants.LAPP_TOKEN, "");
        return getClient().getLevels(token).map(BaseResponseList::getData)
                .flatMap(Observable::from)
                .map(baseResponse -> convertFromObject(baseResponse, Level.class)).toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<List<Evaluation>> getEvaluationTimeline() {
        String token = Prefs.getString(Constants.LAPP_TOKEN, "");
        return getClient().getEvaluationTimeline(token).map(BaseResponseList::getData)
                .flatMap(Observable::from)
                .map(baseResponse -> convertFromObject(baseResponse, Evaluation.class)).toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) ;
    }

    public static Observable<List<ProfessorEvaluation>> getProfessorEvaluationTimeline(String username, String level) {
        String token = Prefs.getString(Constants.LAPP_TOKEN, "");
        return getClient().getProfessorEvaluationTimeline(token, username, level).map(BaseResponseList::getData)
                .flatMap(Observable::from)
                .map(baseResponse -> convertFromObject(baseResponse, ProfessorEvaluation.class)).toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) ;
    }

    public static Observable<List<Student>> getProfessorStudents(String username, int page) {
        String token = Prefs.getString(Constants.LAPP_TOKEN, "");
        Log.d("GETTING>"," PROFESSOR STUDENTS");
        return getClient().getProfessorStudents(token, username, page).map(BaseResponseList::getData)
                .flatMap(Observable::from)
                .map(baseResponse -> convertFromObject(baseResponse, Student.class)).toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) ;
    }


    public static Observable<List<EvolutionData>> getEvolutionInformation(String level) {
        String token = Prefs.getString(Constants.LAPP_TOKEN, "");
        Log.d("GETTING EVOLUTION>",token);
        return getClient().getEvolutionInformation(token, level).map(BaseResponseList::getData)
                .flatMap(Observable::from)
                .map(baseResponse -> convertFromObject(baseResponse, EvolutionData.class)).toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) ;
    }


    public static Observable<List<EvolutionData>> getStudentEvolutionInformation(String level, String studentId) {
        String token = Prefs.getString(Constants.LAPP_TOKEN, "");
        Log.d("GETTING EVOLUTION>",token);
        return getClient().getEvolutionInformation(token, level).map(BaseResponseList::getData)
                .flatMap(Observable::from)
                .map(baseResponse -> convertFromObject(baseResponse, EvolutionData.class)).toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) ;
    }

    public static Observable<AudioFeedback> createAudio(String url) {
        String token = Prefs.getString(Constants.LAPP_TOKEN, "");
        return getClient().createMedia(url, Constants.AUDIO).map(baseResponse -> convertFromBase(baseResponse, AudioFeedback.class))
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<List<Group>> getGroups(String level) {
        String token = Prefs.getString(Constants.LAPP_TOKEN, "");
        return getClient().getGroupsByLevel(token, level).map(BaseResponseList::getData)
                .flatMap(Observable::from)
                .map(baseResponse -> convertFromObject(baseResponse, Group.class)).toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) ;
    }

    public static Observable<List<VideoFeedback>> getVideoFeedbackList(String exerciseId) {
        String token = Prefs.getString(Constants.LAPP_TOKEN, "");
        return getClient().getVideoFeedbackList(token, exerciseId).map(BaseResponseList::getData)
                .flatMap(Observable::from)
                .map(baseResponse -> convertFromObject(baseResponse, VideoFeedback.class)).toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) ;
    }

    public static Observable<AccessToken> login(String email, String password) {
        return getClient().login(email, password)
                .map(baseResponse -> convertFromBase(baseResponse, AccessToken.class))
                .doOnNext(accessToken -> Prefs.putString(Constants.LAPP_TOKEN, "Bearer "+accessToken.token))
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    private static <T> T convertFromBase(BaseResponse baseResponse, Class<T> target) {
        String json = new Gson().toJson(baseResponse.getData());
        return new Gson().fromJson(json, target);
    }

    private static <T> T convertFromObject(Object baseResponse, Class<T> target) {
        String json = new Gson().toJson(baseResponse);
        return new Gson().fromJson(json, target);
    }

    public static Single<SuccessResponse> logout() {
        String token = Prefs.getString(Constants.DEVICE_TOKEN, "");
        return getClient().doLogout("android", token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Single<SuccessResponse> changePassword(String oldPassword, String newPassword) {
        String token = Prefs.getString(Constants.LAPP_TOKEN, "");
        return getClient().changePassword(token, oldPassword, newPassword)
                .map(baseResponse -> convertFromBase(baseResponse, SuccessResponse.class))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Single<SuccessResponse> changeEmail(String email) {
        String token = Prefs.getString(Constants.LAPP_TOKEN, "");
        return getClient().changeEmail(token, email)
                .map(baseResponse -> convertFromBase(baseResponse, SuccessResponse.class))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<ImageUpload> uploadImage(String path) {
        File file = new File(path);

        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse(getMimeType(path)), file));

        return getClient().uploadMedia(filePart)
                .map(baseResponse -> convertFromBase(baseResponse, ImageUpload.class))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Media> uploadMedia(String path) {
        File file = new File(path);

        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse(getMimeType(path)), file));

        return getClient().uploadMedia(filePart)
                .map(baseResponse -> convertFromBase(baseResponse, Media.class))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }
}
