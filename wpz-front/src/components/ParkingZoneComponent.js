import React, {useEffect} from "react";
import {useState} from "react";
import Rectangle from "./Rectangle";

const Figure = (props) => {
    const [devices, setDevices] = useState([]);
    const deviceUrl = "http://localhost:8185/api/v1/device/zone/" + props.zone;

    useEffect(() => {
        async function fetchData() {
            await fetch(deviceUrl, {method: 'get'})
                .then((response) => {
                    const jsonString = response.json();
                    return jsonString;
                }).then(
                    (data) => {
                        setDevices(data);
                    }
                )
                .catch((err) => {
                    console.log(err.message);
                });
        }
        fetchData();
    },[deviceUrl]);

    return (
        <div>
            {devices &&  <Rectangle devices={devices} zone={props.zone}/>}
        </div>
    );

}





export default Figure;