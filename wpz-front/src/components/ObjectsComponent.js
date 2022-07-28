import React from "react";
import Tab from 'react-bootstrap/Tab';
import Tabs from 'react-bootstrap/Tabs';
import Figure from "./ParkingZoneComponent";




class ObjectsTabs extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            objects: [],
            devices: {},
        }
    }


    render() {
        if (this.props.objects.length > 0) {
            return (

                    <Tabs id="objects-tab-controlled"
                          class-name="mb-3"
                    // defaultActiveKey={this.props.objects[0].id}
                    // onSelect={this.handleChange}
                        >
                        {
                            this.props.objects.map((item) => {
                                return (
                                    <Tab eventKey={item.id} title={item.name} key={item.id} >
                                        <h1> Here should be parking zone for {item.id}</h1>
                                        <Figure objectId={item.id} />
                                    </Tab>
                                );
                            })
                        }
                    </Tabs>

            );
        }

    }
}
export default ObjectsTabs;