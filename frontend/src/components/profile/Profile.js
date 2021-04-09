import AuthService from "../../services/AuthService";
import './Profile.css'
import '../form/button/Button.css';
import React from "react";
import RequestService from "../../services/RequestService";
import {Link} from "react-router-dom";
import {Spinner} from "../spinner/Spinner";
import axios from "axios";
import {Post} from "../post/Post";
import {CreatePost} from "../createPost/CreatePost";

export class Profile extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            user: null,
            posts: [],
            loadQueue: 1,
            error: null
        }
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (this.props.match.params.username !== prevProps.match.params.username) {
            this.setState({loadQueue: this.state.loadQueue + 1});
            this.componentDidMount();
        }
    }

    componentDidMount() {
        this.loadUserInfo();
    }

    loadUserInfo() {
        RequestService.getAxios()
            .get(RequestService.URL + `/user/${this.props.match.params.username}`)
            .then(response => {
                if (response.data) {
                    this.setState({
                        user: response.data,
                        loadQueue: this.state.loadQueue - 1
                    }, () => {
                        this.loadUserPosts();
                        this.loadFriends();
                    })
                } else {
                    this.setState({loadQueue: this.state.loadQueue - 1, error: "User not found"})
                }
            })
            .catch(error => {
                const errors = [];
                for (let k in error.response.data.errors) { errors.push(error.response.data.errors[k]); }
                this.setState({error: errors, loadQueue: this.state.loadQueue - 1})
            })
    }

    loadUserPosts() {
        RequestService.getAxios()
            .get(RequestService.URL + "/post", {params: {userId: this.state.user.id}})
            .then(response => this.setState({posts: response.data}));
    }

    loadFriends() {
        RequestService.getAxios()
            .get(RequestService.URL + `/user/friend` , {
                params: {
                    page: 0,
                    size: 10,
                    userId: this.state.user.id
                }
            })
            .then(response => this.setState({friends: response.data}));
    }

    handlePostDelete(id) {
        this.setState({posts: this.state.posts.filter(post => post.id !== id)});
    }

    handlePostCreate(post) {
        this.setState({posts: [post, ...this.state.posts]});
    }

    render() {
        if (this.state.loadQueue === 0) {
            return (
                <div className="profile">
                    {
                        this.state.error === null
                            ? <div className="profile">
                                <div className="profile__info">
                                    <div className="profile__info__header">
                                        <p className="profile__info__name">{`${this.state.user.firstName} ${this.state.user.lastName}`}</p>
                                        <div>
                                            {
                                                AuthService.isAuthenticated() && AuthService.getUsername() === this.state.user.username
                                                    ? <Link to={`/user/edit`} className="button">Edit info</Link>
                                                    : <Link to={`/user/${this.state.user.username}/message`}
                                                            className="button">Send
                                                        message</Link>
                                            }
                                        </div>
                                    </div>
                                    <div className="profile__info__block">
                                        <span>Birthday:</span>
                                        <span>{this.state.user.birthday ? new Date(this.state.user.birthday).toLocaleDateString() : "not specified"}</span>
                                    </div>
                                    <div className="profile__info__block">
                                        <span>City:</span>
                                        <span>{this.state.user.city ? this.state.user.city : "not specified"}</span>
                                    </div>
                                </div>
                                {
                                    AuthService.isAuthenticated() && AuthService.getUsername() === this.state.user.username
                                        ? <CreatePost handleSubmit={this.handlePostCreate.bind(this)}/>
                                        : ''
                                }
                                {
                                    this.state.posts.map(friend => <div key={friend.id}>{friend.username}</div>)
                                }
                                {
                                    this.state.posts.map(post => <Post key={post.id} data={post}
                                                                       handleDelete={this.handlePostDelete.bind(this)}/>)
                                }
                            </div>
                            : <div>{this.state.error}</div>
                    }
                </div>
            );
        } else {
            return ( <Spinner/> )
        }
    }
}