import React, {useEffect, useState} from "react";
import {Layer, Rect, Stage} from "react-konva";


const Rectangle = (props) => {

    function generateRectangle() {

        return props.devices.map((device) => (
                    {
                        id: device.id.toString(),
                        name: device.deviceNumber,
                        width: 30,
                        height:50,
                        x: device.xcor,
                        y: device.ycor,
                        fill: device.status === 0 ? "green":"red",
                        isDragging: false
                    }
                )
            );
    }

    const init_state = generateRectangle();
    const [rects, setRects] = useState([]);

    useEffect(()=>{
        const interval = setInterval(
            () => {
                setRects(init_state);
            }, 10000
        );
        return () => clearInterval(interval);
    });




    const handleDragStart = (event) => {
        const id = event.target.id;
        setRects(
            rects.map((rect) => {
                return {
                    ...rect,
                    isDragging: rect.id === id,
                };
            })
        );

    };

    const handleDragEnd = (event) => {
        const baseUrl = "http://localhost:8185/api/v1/device/" + event.target.id();
        console.log()
        setRects(
            rects.map((rect) => {
                //TODO: send current positions to Device Entity
                if (rect.id === event.target.id()) {
                    const requestParams = {
                        method: "PUT",
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify(
                            { devNumber: rect.name,
                                    zoneId: props.zone,
                                    xcor: event.target.x(),
                                    ycor: event.target.y()}
                        )
                    };
                    fetch(baseUrl, requestParams)
                        .catch(error => {
                            console.error('There was an error!', error);
                        });
                }
                return {
                    ...rect,
                    isDragging: false,
                };
            })
        );



    };

    return (
        <Stage width={window.innerWidth} height={window.innerHeight}>
            <Layer>
                {
                    rects.map((rect) => (
                        <Rect id={rect.id}
                              key={rect.id}
                              width={rect.width}
                              height={rect.height}
                              x={rect.x}
                              y={rect.y}
                              shadowBlur={rect.shadowBlur}
                              fill={rect.fill}
                              draggable
                              onDragStart={handleDragStart}
                              onDragEnd={handleDragEnd}
                        />
                    ))
                }

            </Layer>
        </Stage>
    );
}

export default Rectangle;