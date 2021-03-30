import './AdminPanel.css';
import * as React from "react";
import {Link} from "react-router-dom";

export class AdminPanel extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            active: false
        }
    }

    handleClickShow() {
        this.setState({active: !this.state.active})
    }

    render() {
        return (
            <div className={`admin-panel ${this.state.active ? 'admin-panel-show' : ''}`}>
                {
                    this.state.active
                        ?   <div className={"admin-panel__links"}>
                                <Link to={"/admin/city"} className={"admin-panel__link"} onClick={this.handleClickShow.bind(this)}>Cities</Link>
                            </div>
                        : ''
                }
                <div className={"admin-panel__button-show"} onClick={this.handleClickShow.bind(this)}>
                    {
                        this.state.active
                            ?   '<'
                            :   '>'
                    }
                </div>
            </div>
        );
    }
}