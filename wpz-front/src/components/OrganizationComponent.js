import React from "react";
import Form from 'react-bootstrap/Form';




class OrganizationList extends React.Component{
    constructor(props) {
        super(props);
        this.handleChange = this.handleChange.bind(this);
        this.state = {
            orgList:[]
        }


    }

    componentDidMount() {
        const apiUrl = "http://localhost:8185/api/v1/organization";
        fetch(apiUrl, {method: 'get', mode:'cors'}
        )
            .then((response) => response.json())
            .then((data) => this.setState({orgList:data}));
    }

    handleChange(e) {
        this.props.onOrganizationSelect(e.target.value);
    }

    render() {

        return (
            <Form.Select aria-label="Default select example" onChange={this.handleChange}>

                <option>Выберите организацию</option>
                {
                    this.state.orgList.map((item) => {
                        let itemKey = String(item.id) + "$$" + item.orgName;
                        return <option key={item.id} value={itemKey}>{item.orgName}</option>
                    })
                }
            </Form.Select>
        );
    }

}

export default OrganizationList;