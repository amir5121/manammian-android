package com.amir.manammiam.services;

import android.view.View;

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

    public static class PostResponse extends ManamMiamResponse {
        ArrayList<ManamMiamPost> posts;

        public PostResponse(ArrayList<ManamMiamPost> posts) {
            this.posts = posts;
        }

        public ArrayList<ManamMiamPost> getPosts() {
            return posts;
        }
    }


    public static class AcceptRequest {
        private final ManamMiamPost post;
        private final View loading;
        private final String token;

        public AcceptRequest(ManamMiamPost post, View loading, String token) {
            this.post = post;
            this.loading = loading;
            this.token = token;
        }

        public ManamMiamPost getPost() {
            return post;
        }

        public View getLoading() {
            return loading;
        }

        public String getToken() {
            return token;
        }
    }

    public static class AcceptResponse extends ManamMiamResponse {
        public static final int SUCCESSFUL = 1;

        private final int result;
        private final View loading;
        private final ManamMiamPost post;

        public AcceptResponse(int result, View loading, ManamMiamPost post) {
            this.result = result;
            this.loading = loading;
            this.post = post;
        }

        public int getResult() {
            return result;
        }

        public View getLoading() {
            return loading;
        }

        public ManamMiamPost getPost() {
            return post;
        }
    }
}
