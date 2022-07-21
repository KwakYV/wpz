import React from "react";
import Form from 'react-bootstrap/Form';


// *******************
//Это должно быть переписано на вызов REST API
const jsonString = "[{\"id\": 1, \"orgName\": \"Ashan\", \"desc\": \"Сеть\"}," +
    "{\"id\": 2, \"orgName\": \"X5 Retail\", \"desc\": \"Сеть\"}," +
    "{\"id\": 3, \"orgName\": \"Mega\", \"desc\": \"Сеть\"}" +
    "]";
const organizationList = JSON.parse(jsonString);
// *******************

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

                <option>Выберите организацию</option>
                {
                    organizationList.map((item) => {
                        let itemKey = String(item.id) + "$$" + item.orgName;
                        return <option key={item.id} value={itemKey}>{item.orgName}</option>
                    })
                }
            </Form.Select>
        );
    }

}

export default OrganizationList;