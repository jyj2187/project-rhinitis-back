package com.rhinitis.projectrhinitis.util.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    /**
     * 400 Bad Request 클라이언트 요청 문법 오류.
     * 401 인증되지 않음 - 인증
     * 403 권한 없음 - 인가
     * 404 존재하지 않음
     * 409 클라이언트 - 서버 상태 충돌 (유저명 중복 등)
     */

    //TODO divisionCODE 규칙성

    // 권한 관련
    NO_READ_PERMISSION(401,"AU001RE","열람 권한 없음"),
    NO_POST_PERMISSION(403,"AU002PO","게시글 등록 권한 없음"),
    NO_EDIT_PERMISSION(403,"AU003ED","수정 권한 없음"),
    PERMISSION_DENIED(403, "", "권한 없음"),

    // Member 관련
    EXISTING_USERNAME(409,"ME001UN","이미 존재하는 USERNAME"),
    WRONG_PASSWORD(401,"ME401PW","잘못된 패스워드"),
    WRONG_ACTIVATION_CODE(403,"ME410AC","잘못된 활성화 코드"),
//    WRONG_REQUEST(404,"ME999RE","잘못된 요청"),
    MEMBER_NOT_EXIST(404,"ME099NO","존재하지 않는 회원"),
    ALREADY_ACTIVATED(409, "", "이미 활성화된 회원입니다."),

    // Post 관련
    POST_NOT_EXIST(404,"","존재하지 않는 게시글"),

    // Comment
    COMMENT_NOT_EXIST(404, "", "존재하지 않는 댓글"),

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
