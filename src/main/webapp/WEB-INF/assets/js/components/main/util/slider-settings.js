import React from "react";

const SamplePrevArrow = (props) => {
    const {className, style, onClick} = props;
    return (
        <div
            className={className}
            style={{...style}}
            onClick={onClick}
        ><div className="arrow-slide"><i className="fas fa-arrow-left"/></div></div>
    );
};

const SampleNextArrow = (props) => {
    const {className, style, onClick} = props;
    return (
        <div
            className={className}
            style={{...style}}
            onClick={onClick}
        ><div className="arrow-slide"><i className="fas fa-arrow-right"/></div></div>
    );
};

export const getSettings = (length, max = 6) => {
    const slidesToShow = length >= max ? max : length;
    const slidesToShow1024 = length >= max - 1 ? max - 1 : length;
    const slidesToShow800 = length >= max - 2 ? max - 2 : length;
    const slidesToShow600 = length >= max - 3 ? max - 3 : length;
    const slidesToShow400 = length >= 1 ? 1 : length;
    return {
        slidesToShow: slidesToShow,
        arrows: true,
        nextArrow: <SampleNextArrow/>,
        prevArrow: <SamplePrevArrow/>,
        variableWidth: true,
        responsive:
            [{
                breakpoint: 1024,
                settings: {
                    slidesToShow: slidesToShow1024,
                }
            },
                {
                    breakpoint: 800,
                    settings: {
                        slidesToShow: slidesToShow800,
                    }
                },
                {
                    breakpoint: 600,
                    settings: {
                        slidesToShow: slidesToShow600,
                    }
                },
                {
                    breakpoint: 480,
                    settings: {
                        slidesToShow: slidesToShow400,
                    }
                }
            ]
    }
};

export const getItemWidth = (deviceWidth) => {
    if (deviceWidth > 1600)
        return 250;
    if (deviceWidth > 1400)
        return 230;
    if (deviceWidth > 1024)
        return 210;
    if (deviceWidth > 800)
        return 180;
    return 150;
};

export const getItemRectangleWidth = (deviceWidth) =>{
    if (deviceWidth > 1600)
        return 305;
    if (deviceWidth > 1024)
        return 250;
    if (deviceWidth > 800)
        return 220;
    return 200;
};