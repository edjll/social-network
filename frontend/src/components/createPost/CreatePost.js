import * as React from "react";
import {PostForm} from "../postForm/PostForm";
import RequestService from "../../services/RequestService";
import AuthService from "../../services/AuthService";
import './CreatePost.css';

export const CreatePost = (props) => {

    const handleSubmit = (text) => {
        RequestService.getAxios().post(RequestService.URL + "/post/save", {
            userId: AuthService.getId(),
            text: text
        }).then(response => {
            if (props.handleSubmit) props.handleSubmit(response.data);
        });
    }

    return (
        <div className={`create-post ${props.className ? props.className : ''}`}>
            <PostForm handleSubmit={handleSubmit.bind(this)}/>
        </div>
    );
}