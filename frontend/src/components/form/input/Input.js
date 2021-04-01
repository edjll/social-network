import React from "react";
import './Input.css'

export class Input extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            active: false,
            value: this.props.value ? this.props.value : ""
        };
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (prevProps.value !== this.props.value) {
            this.setState({value: this.props.value});
        }
    }

    handleFocus() {
        this.setState({active: true});
    }

    handleBlur() {
        this.setState({active: false});
        if (this.props.handleBlur) this.props.handleBlur();
    }

    handleChange(value) {
        this.setState({value: value})
        if (this.props.handleChange) this.props.handleChange(value);
    }

    render() {

        return (
            <label className={`input ${this.props.className ? this.props.className : ''}`}>
                <span className={"input__label"}>{this.props.label}</span>
                <input
                    className={"input__value"}
                    value={this.state.value}
                    name={this.props.name}
                    type={this.props.type}
                    onChange={(e) => this.handleChange(e.target.value)}
                    onFocus={this.handleFocus.bind(this)}
                    onBlur={this.handleBlur.bind(this)}
                    disabled={!!this.props.disabled}
                />
                {
                    this.props.error
                        ?   <div className={"input__error"}>
                                <div className={"input__error__description"}>{this.props.error}</div>
                                <div className={"input__error__icon"}>❌</div>
                            </div>
                        :   ''
                }
            </label>
        )
    }
}