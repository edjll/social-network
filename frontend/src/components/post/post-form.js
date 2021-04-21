import * as React from "react";
import {Form} from "../form/form";
import {CardBody} from "../card/card-body";
import {FormTextarea} from "../form/form-textarea";
import {CardFooter} from "../card/card-footer";
import {FormButton} from "../form/form-button";
import Validator from "../../services/Validator";
import validation from "../../services/validation.json";
import './post-form.css';

export class PostForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            text: this.props.text ? this.props.text : "",
            error: null
        };
    }

    handleChangeText(value) {
        this.setState({text: value, error: null});
    }

    handleSubmit(e) {
        e.preventDefault();
        if (this.validate() === 0) {
            this.props.handleSubmit(this.state.text.replace(/\n\n+/g, '\n'));
            this.setState({text: ''})
        }
    }

    validate() {
        let size = 0;

        const textError = Validator.validate('Text', this.state.text, validation.post.text.params);
        if (textError) {
            this.setState({error: textError})
            size++;
        }

        return size;
    }

    render() {
        return (
            <Form className={"post_form"} handleSubmit={this.handleSubmit.bind(this)}>
                {this.props.children}
                <CardBody>
                    <FormTextarea error={this.state.error} handleChange={this.handleChangeText.bind(this)} value={this.state.text}/>
                </CardBody>
                <CardFooter>
                    <FormButton>Save</FormButton>
                </CardFooter>
            </Form>
        );
    }
}