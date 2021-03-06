import './group-post.css';
import * as React from "react";
import RequestService from "../../../services/RequestService";
import {Post} from "../../post/post";
import PostType from "../../../services/PostType";

export const GroupPost = (props) => {

    const handleSubmit = (context, text) => {
        RequestService
            .getAxios()
            .put(RequestService.URL + `/groups/posts/${context.state.post.id}`, {
                text: text
            }).then(response => {
            context.setState({post: {...response.data, type: PostType.GROUP}});
        });
        context.setState({editable: false});
    }

    const handleDelete = (context) => {
        RequestService
            .getAxios()
            .delete(RequestService.URL + `/groups/posts/${context.state.post.id}`)
            .then(() => {
                if (props.handleDelete) props.handleDelete(props.id);
            });
    }

    return (
        <Post data={props.data} handleSubmit={handleSubmit} handleDelete={handleDelete}/>
    );
}