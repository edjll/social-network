import * as React from "react";
import {Card} from "../card/card";
import {CardHeader} from "../card/card-header";
import {CardBody} from "../card/card-body";
import './group.css'
import RequestService from "../../services/RequestService";
import AuthService from "../../services/AuthService";
import {Link, Route, Switch} from "react-router-dom";
import {GroupUpdater} from "./group-updater";
import {GroupRemover} from "./group-remover";
import {GroupPostCreator} from "./group-post/group-post-creator";
import {GroupPost} from "./group-post/group-post";
import {FormButton} from "../form/form-button";
import {UserCardMini} from "../user/user-card/user-card-mini";
import IntersectionObserverService from "../../services/IntersectionObserverService";
import PostType from "../../services/PostType";
import {Error} from "../error/error";

export class Group extends React.Component {

    constructor(props) {
        super(props);
        this.defaultState = {
            page: 0,
            size: 5,
            totalPages: 0,
            posts: [],
            error: null
        }
        this.state = {
            ...this.defaultState,
            info: {
                title: undefined,
                description: undefined,
                id: undefined,
                address: undefined,
                creatorId: undefined
            },
            subscribed: null,
            totalUsers: 0,
            users: []
        };
    }

    componentDidMount() {
        this.loadInfo()
            .then(() => {
                this.loadPosts(() => IntersectionObserverService.create('.post:last-child', this, this.loadPosts));
                this.loadUsers();
            })
            .catch(error => this.setState({error: error.response.data.message, loadQueue: this.state.loadQueue - 1}));
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (this.props.match.params.address !== prevProps.match.params.address || (this.props.location.state && this.props.location.state.updated)) {
            this.setState({...this.state, ...this.defaultState}, () => this.componentDidMount());
            this.props.history.push(this.props.location.pathname);
        }
    }

    loadInfo() {
        return RequestService.getAxios()
            .get(RequestService.URL + "/groups/" + this.props.match.params.address)
            .then(response => new Promise((resolve) => this.setState({info: response.data}, () => resolve())))
    }

    loadPosts(callback) {
        RequestService
            .getAxios()
            .get(RequestService.URL + `/groups/${this.state.info.id}/posts`, {
                params: {
                    page: this.state.page,
                    size: this.state.size
                }
            })
            .then(response => this.setState({
                posts: [...this.state.posts, ...response.data.map(post => { return {...post, type: PostType.GROUP}})],
                lastSize: response.data.length
            }, () => {
                if (callback) callback();
            }));
    }

    loadUsers() {
        RequestService
            .getAxios()
            .get(RequestService.URL + `/groups/${this.state.info.id}/users/cards`, {
                params: {
                    size: 9
                }
            })
            .then(response => {
                if (AuthService.isAuthenticated()) {
                    const user = response.data.users.find(u => u.username === AuthService.getUsername());
                    if (user !== undefined) {
                        this.setState({
                            users: [user, ...response.data.users.filter(u => u.username !== AuthService.getUsername())],
                            totalUsers: response.data.count,
                            subscribed: true
                        });
                        return;
                    }
                }
                this.setState({
                    users: response.data.users,
                    totalUsers: response.data.count,
                    subscribed: false
                })
            });
    }

    handleCreatedGroupPost(post) {
        this.setState({posts: [{...post, type: PostType.GROUP}, ...this.state.posts]});
    }

    handleDeletePost(id) {
        this.setState({posts: this.state.posts.filter(post => post.id !== id)});
    }

    handleSubscribe() {
        if (!AuthService.isAuthenticated()) AuthService.toLoginPage(this.props.history);
        else
            RequestService
                .getAxios()
                .post(RequestService.URL + `/groups/${this.state.info.id}/users`)
                .then(() => this.setState({subscribed: true}, this.loadUsers.bind(this)));
    }

    handleUnsubscribe() {
        RequestService
            .getAxios()
            .delete(RequestService.URL + `/groups/${this.state.info.id}/users`)
            .then(() => this.setState({subscribed: false}, this.loadUsers.bind(this)));
    }

    render() {
        if (this.state.error !== null) {
            return (
                <Error>
                    <p className={'error__text'}>
                        {this.state.error}
                    </p>
                    <Link to={'/'} className={'error__link'}>Go home</Link>
                </Error>
            );
        }
        return (
            <div className={"group"}>
                <div className={"left_side"}>
                    <Card>
                        <CardHeader>
                            <h3>Actions</h3>
                        </CardHeader>
                        <CardBody>
                            {
                                this.state.info.creatorId === AuthService.getId()
                                    ? <Link to={`/group/${this.state.info.address}/update`}
                                            className={"form__button"}>Edit</Link>
                                    : ''
                            }
                            {
                                this.state.info.creatorId === AuthService.getId()
                                    ? <Link to={`/group/${this.state.info.address}/delete`}
                                            className={"form__button"}>Delete</Link>
                                    : ''
                            }
                            {
                                this.state.subscribed !== null
                                    ?   this.state.subscribed
                                        ? <FormButton
                                            handleClick={this.handleUnsubscribe.bind(this)}>Unsubscribe</FormButton>
                                        : <FormButton
                                            handleClick={this.handleSubscribe.bind(this)}>Subscribe</FormButton>
                                    :   ''
                            }
                        </CardBody>
                    </Card>
                    {
                        this.state.totalUsers > 0
                            ?   <Card>
                                    <CardHeader>
                                        <Link to={{
                                            pathname: `/group/subscribers`,
                                            search: `?id=${this.state.info.id}`
                                        }}>Subscribers</Link>
                                        <span>{this.state.totalUsers}</span>
                                    </CardHeader>
                                    <CardBody className={"group__users"}>
                                        {
                                            this.state.users.map(user => <UserCardMini key={user.username} info={user}/>)
                                        }
                                    </CardBody>
                                </Card>
                            :   ''
                    }
                </div>
                <div className={"right_side"}>
                    <Card className={"group__info"}>
                        <CardHeader className={"group__info__title"}>
                            {
                                this.state.info.title !== undefined
                                    ?   <h1>{this.state.info.title}</h1>
                                    :   <h1 className={'card_preloader'}>...</h1>
                            }
                        </CardHeader>
                        <CardBody className={"group__info__description"}>
                            {
                                this.state.info.description !== undefined
                                    ?   <pre>{this.state.info.description}</pre>
                                    :   <pre className={'card_preloader'}>...</pre>
                            }
                        </CardBody>
                    </Card>
                    {
                        AuthService.getId() === this.state.info.creatorId
                            ? <GroupPostCreator groupId={this.state.info.id}
                                                handleSubmit={this.handleCreatedGroupPost.bind(this)}/>
                            : ''
                    }
                    {
                        this.state.posts.map(post =>
                            <GroupPost key={post.id}
                                       id={post.id}
                                       data={post}
                                       handleDelete={this.handleDeletePost.bind(this)}
                            />)
                    }
                </div>
                <Switch>
                    <Route path={"/group/:address/update"} component={(props) => <GroupUpdater {...props} handleUpdate={() => this.componentDidMount()
                    }/>}/>
                    <Route path={"/group/:address/delete"} component={GroupRemover}/>
                </Switch>
            </div>
        );
    }
}