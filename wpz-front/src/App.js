import React from "react";
import OrganizationList from "./components/OrganizationComponent";

import ObjectsTabs from "./components/ObjectsComponent";
import 'bootstrap/dist/css/bootstrap.min.css';

class App extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            organization: "",
            object: "",
        }
        this.handleOrganizationSelect = this.handleOrganizationSelect.bind(this);
    }

    handleOrganizationSelect(organization) {
        this.setState({organization:organization});
    }

    render() {
        return (
            <div>
                <h1>{this.props.value}</h1>

                <hr />
                <OrganizationList onOrganizationSelect={this.handleOrganizationSelect}/>
                <br />
                <hr />
                <h1>{getValueFromConcatString(this.state.organization, "$$", 1)}</h1>
                <ObjectsTabs />

            </div>
        );
    }
}


function getValueFromConcatString(aString, aSep, aPos) {
    const fields = aString.split(aSep);
    if (aPos < fields.length) {
        return fields[aPos];
    } else {
        return aString
    }
}

export default App;
