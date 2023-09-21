package com.abc.jdbc.dto;

public class CommentsDTO {
    private String id;
    private String postId;
    private String membersId;
    private String commentsText;
    private String commentsTime;

    public String getId() {
        return id;
    }

    public String getPostId() {
        return postId;
    }

    public String getMembersId() {
        return membersId;
    }

    public String getCommentsText() {
        return commentsText;
    }

    public String getCommentsTime() {
        return commentsTime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public void setMembersId(String membersId) {
        this.membersId = membersId;
    }

    public void setCommentsText(String commentsText) {
        this.commentsText = commentsText;
    }

    public void setCommentsTime(String commentsTime) {
        this.commentsTime = commentsTime;
    }
}
