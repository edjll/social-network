import RequestService from "../../services/RequestService";
import * as React from "react";
import {Form} from "../form/form";
import {CardHeader} from "../card/card-header";
import {FormClose} from "../form/form-close";
import {CardBody} from "../card/card-body";
import {FormInput} from "../form/form-input";
import {CardFooter} from "../card/card-footer";
import {FormButton} from "../form/form-button";
import Validator from "../../services/Validator";
import validation from "../../services/validation.json";

export class CountryUpdater extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            id: null,
            countries: [],
            loadQueue: 1,
            title: '',
            errors: {
                title: null
            }
        }
    }

    componentDidMount() {
        document.body.style.overflow = 'hidden';
        RequestService.getAxios().get(RequestService.URL + "/countries/" + this.props.match.params.id)
            .then(response => {
                this.setState({
                    id: response.data.id,
                    title: response.data.title,
                    loadQueue: this.state.loadQueue - 1
                });
            });
    }

    componentWillUnmount() {
        document.body.style.overflow = 'auto';
    }

    handleClose() {
        this.props.history.push("/admin/countries");
    }

    handleSubmit(e) {
        e.preventDefault();
        if (this.validate() === 0) {
            RequestService
                .getAxios()
                .put(RequestService.ADMIN_URL + `/countries/${this.state.id}`, {
                    title: this.state.title
                })
                .then(() => this.props.history.push("/admin/countries", { update: true }))
                .catch(error => this.setState({
                    errors: {
                        title: error.response.data.errors.title
                    }
                }));
        }
    }

    validate() {
        let size = 0;
        let errors = {...this.state.errors};
        const titleError = Validator.validate('Title', this.state.title, validation.country.title.params);
        if (titleError) {
            errors = {...errors, title: titleError};
            size++;
        }

        this.setState({errors: errors});
        return size;
    }

    handleChangeTitle(value) {
        this.setState({title: value, errors: {title: null}});
    }

    render() {
        return (
            <div className={"attention_center"}>
                <Form handleSubmit={this.handleSubmit.bind(this)}>
                    <CardHeader>
                        <h1>Updating country</h1>
                        <FormClose handleClick={this.handleClose.bind(this)}/>
                    </CardHeader>
                    <CardBody>
                        <FormInput
                            value={this.state.id}
                            title={"id"}
                            disabled={true}
                        />
                        <FormInput
                            clearable={true}
                            value={this.state.title}
                            handleChange={this.handleChangeTitle.bind(this)}
                            title={"title"}
                            error={this.state.errors.title}
                            pattern={"[a-zA-Z ]"}
                        />
                    </CardBody>
                    <CardFooter>
                        <FormButton>Update</FormButton>
                    </CardFooter>
                </Form>
            </div>
        );
    }
}