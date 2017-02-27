package com.amir.manammiam.services;

import com.amir.manammiam.infrastructure.post.ManamMiamPost;

import java.util.ArrayList;

public final class Posts {
    private Posts() {
    }

    public static class PostsRequest {
        String token;

        public PostsRequest(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }
    }

    public static class PostResponse extends Response {
        ArrayList<ManamMiamPost> posts;

        public PostResponse(ArrayList<ManamMiamPost> posts) {
            this.posts = posts;
        }

        public ArrayList<ManamMiamPost> getPosts() {
            return posts;
        }
    }
}
