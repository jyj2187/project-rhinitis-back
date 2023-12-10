package com.rhinitis.projectrhinitis.util.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    /**
     * 401 인증되지 않음
     * 403 권한 없음
     * 404 존재하지 않음
     * 400 기타
     */

    //TODO divisionCODE 규칙성

    //권한 관련
    NO_READ_AUTHORIZATION(401,"AU001RE","열람 권한 없음"),
    NO_POST_AUTHORIZATION(403,"AU002PO","게시글 등록 권한 없음"),
    NO_EDIT_AUTHORIZATION(403,"AU003ED","수정 권한 없음"),


    //Member 관련
    EXISTING_USERNAME(400,"ME001UN","이미 존재하는 USERNAME"),

    WRONG_PASSWORD(401,"ME401PW","잘못된 패스워드"),
    WRONG_ACTIVE_CODE(403,"ME410AC","잘못된 활성화 코드"),
    WRONG_REQUEST(404,"ME999RE","잘못된 요청"),

    NO_MEMBER_EXIST(404,"ME099NO","존재하지 않는 회원"),

    //Post 관련


    //기타
    ;

    private int status;
    private String divisionCode;
    private String message;

    ExceptionCode(int status,String divisionCode, String message) {
        this.status = status;
        this.divisionCode = divisionCode;
        this.message = message;
    }
}
