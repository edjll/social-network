import * as React from "react";
import {CardBody} from "../card/card-body";
import {FormInput} from "../form/form-input";
import {Form} from "../form/form";
import RequestService from "../../services/RequestService";
import './search-page.css';
import {Card} from "../card/card";
import {CardHeader} from "../card/card-header";
import {FormSelect} from "../form/form-select";
import {FormButton} from "../form/form-button";
import IntersectionObserverService from "../../services/IntersectionObserverService";

export class SearchPage extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            users: [],
            countries: [],
            cities: [],
            page: 0,
            size: 12,
            totalPages: 0,
            id: props.id,
            firstName: null,
            lastName: null,
            countryId: null,
            cityId: null
        }

        this.loadUsers = props.loadUsers.bind(this);
    }

    componentDidMount() {
        this.loadUsers(() => IntersectionObserverService.create('.user_card:last-child', this, this.loadUsers));
        this.loadCountries();
    }

    loadCountries() {
        RequestService.getAxios()
            .get(RequestService.URL + "/countries")
            .then(response => this.setState({countries: response.data}));
    }

    loadCities() {
        RequestService.getAxios()
            .get(RequestService.URL + "/cities", {
                params: {
                    countryId: this.state.countryId
                }
            })
            .then(response => this.setState({cities: response.data}));
    }

    handleChangeFirstName(value) {
        this.setState({firstName: value});
    }

    handleChangeLastName(value) {
        this.setState({lastName: value});
    }

    handleChangeCountry(option) {
        if (option == null) {
            this.setState({
                countryId: null,
                cityId: null
            });
        } else {
            this.setState({countryId: option.key}, this.loadCities);
        }
    }

    handleChangeCity(option) {
        if (option == null) this.setState({cityId: null});
        else this.setState({cityId: option.key});
    }

    handleSearch() {
        this.setState({users: [], page: 0}, this.loadUsers);
    }

    render() {
        return (
            <div className={"search_page"}>
                <div className={"left_side"}>
                    <Form handleSubmit={this.handleSearch.bind(this)}>
                        <CardBody>
                            <FormInput value={this.state.firstName} title={"first name"} handleChange={this.handleChangeFirstName.bind(this)}/>
                            <FormInput value={this.state.lastName} title={"last name"} handleChange={this.handleChangeLastName.bind(this)}/>
                            {
                                this.state.countries.length > 0
                                    ? <FormSelect
                                        title={"country"}
                                        options={this.state.countries.map(country => {
                                            return {key: country.id, text: country.title}
                                        })}
                                        handleChange={this.handleChangeCountry.bind(this)}
                                    />
                                    : ''
                            }
                            {
                                this.state.countryId && this.state.cities.length > 0
                                    ? <FormSelect
                                        title={"city"}
                                        options={this.state.cities.map(city => {
                                            return {key: city.id, text: city.title}
                                        })}
                                        handleChange={this.handleChangeCity.bind(this)}
                                    />
                                    : ''
                            }
                            <FormButton>Search</FormButton>
                        </CardBody>
                    </Form>
                </div>
                <div className={"right_side"}>
                    <Card>
                        <CardHeader>
                            {this.props.children}
                        </CardHeader>
                        {
                            this.state.users.length > 0
                                ? <CardBody>
                                    {
                                        this.state.users.map(user => this.props.card(user.id, user))
                                    }
                                </CardBody>
                                : <CardBody><p>Not found</p></CardBody>
                        }
                    </Card>
                </div>
            </div>
        );
    }
}