import React from "react";
import Tab from 'react-bootstrap/Tab';
import Tabs from 'react-bootstrap/Tabs';

// *******************
//Это должно быть переписано на вызов REST API
const jsonString = "[{\"id\": 1, \"name\": \"МЕГА - Белая Дача\", \"desc\": \"МЕГА\"}," +
    "{\"id\": 2, \"name\": \"МЕГА - Ясенево\", \"desc\": \"МЕГА\"}," +
    "{\"id\": 3, \"name\": \"МЕГА - Химки\", \"desc\": \"МЕГА\"}" +
    "]";
const objectsList = JSON.parse(jsonString);
// *******************


class ObjectsTabs extends React.Component {
    constructor(props) {
        super(props);
        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(e) {

    }

    render() {
        return (

                <Tabs id="objects-tab-controlled"
                      class-name="mb-3"
                defaultActiveKey={objectsList[0]}>
                    {
                        objectsList.map((item) => {
                            return (
                                <Tab eventKey={item.id} title={item.name} key={item.id}>
                                    <h1> Here should be parking zone for {item.id}</h1>
                                </Tab>
                            );
                        })
                    }
                </Tabs>

        );

    }
}
export default ObjectsTabs;