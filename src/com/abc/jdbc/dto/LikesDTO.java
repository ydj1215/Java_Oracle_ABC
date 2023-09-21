package com.abc.jdbc.dto;

public class LikesDTO {
    private String id;
    private String postId;
    private String membersId;

    public String getId() {
        return id;
    }

    public String getPostId() {
        return postId;
    }

    public String getMembersId() {
        return membersId;
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
}
