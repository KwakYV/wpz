import React from "react";
import OrganizationList from "./components/OrganizationComponent";

class App extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            organization: "",
        }
        this.handleOrganizationSelect = this.handleOrganizationSelect.bind(this);
    }

    handleOrganizationSelect(organization) {
        console.log('Show the selected organization ' + organization)
        this.setState({organization:organization});
    }

    render() {
        return (
            <div>
                <h1>{this.props.value}</h1>
                <OrganizationList onOrganizationSelect={this.handleOrganizationSelect}/>
                <h1>Организация - {this.state.organization}</h1>
            </div>
        );
    }
}
export default App;
