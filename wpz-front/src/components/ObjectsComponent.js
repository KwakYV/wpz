import React from "react";
import Tab from 'react-bootstrap/Tab';
import Tabs from 'react-bootstrap/Tabs';
import Figure from "./ParkingZoneComponent";




class ObjectsTabs extends React.Component {
    constructor(props) {
        super(props);
        this.handleChange = this.handleChange.bind(this);
        this.state = {
            objects: [],
            devices: {},
            zone: 0,
        }
    }

    tick() {
        const temp = this.state.zone
        this.setState({zone:temp})
    }

    componentDidMount() {
        this.timerID = setInterval(
            () => this.tick(),
            10000
        );
    }

    componentWillUnmount() {
        clearInterval(this.timerID);
    }


    handleChange(key, e) {
        const parkingUrl = "http://localhost:8185/api/v1/parking/building/" +  key;
        fetch(parkingUrl, {method: 'get'})
            .then((response) => response.json())
            .then((data) => {
                this.setState({zone: data.id});
            }).catch((err) => {
            console.log(err.message);
        });

    }

    render() {
        if (this.props.objects.length > 0) {
            return (

                    <Tabs id="objects-tab-controlled"
                          class-name="mb-3"
                    // defaultActiveKey={this.props.objects[0].id}
                    onSelect={this.handleChange}>
                        {
                            this.props.objects.map((item) => {
                                return (
                                    <Tab eventKey={item.id} title={item.name} key={item.id} >
                                        <h1> Here should be parking zone for {item.id}</h1>
                                        <Figure objectId={item.id} zone={this.state.zone}/>
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