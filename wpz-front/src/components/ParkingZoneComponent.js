import React, {useEffect} from "react";
import {useState} from "react";
import Rectangle from "./Rectangle";

const Figure = (props) => {
    const [devices, setDevices] = useState([]);
    const [zone, setZone] =useState(0);

    useEffect(
        () => {
            const parkingUrl = "http://localhost:8185/api/v1/parking/building/" + props.objectId;
            fetch(parkingUrl, {method: 'get'})
                .then((response) => response.json())
                .then((data) => {
                    setZone(data.id);
                }).catch((err) => {
                console.log(err.message);
            });
        }
    );



    const deviceUrl = "http://localhost:8185/api/v1/device/zone/" + zone;

    function fetchData(url) {
        fetch(url, {method: 'get'})
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

    useEffect(
        () => {
            fetchData(deviceUrl)
        }, [deviceUrl]
    )

    useEffect(() => {
        const interval = setInterval(
            () => {
                fetch(deviceUrl, {method: 'get'})
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
            }, 30000
        )
        return () => clearInterval(interval);
    },[zone]);

    return (
        <div>
            {devices &&  <Rectangle devices={devices} zone={zone}/>}
        </div>
    );

}





export default Figure;