import React from "react";
import Form from 'react-bootstrap/Form';


class OrganizationList extends React.Component{
    constructor(props) {
        super(props);
        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(e) {
        this.props.onOrganizationSelect(e.target.value);
    }

    render() {
        return (
            <Form.Select aria-label="Default select example" onChange={this.handleChange}>
                <option>Select organization</option>
                <option value="1">One</option>
                <option value="2">Two</option>
                <option value="3">Three</option>
            </Form.Select>
        );
    }

}

export default OrganizationList;