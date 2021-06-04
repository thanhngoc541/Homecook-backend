import React from 'react';
import { Link } from 'react-router-dom';

const Footer = (props) => {
    return (
            <div className="footer fixed-bottom">
                <div className="container">
                    <div className="row justify-content-center">
                        <div className="col-auto">
                            <p>© Copyright 2021 FPT University</p>
                        </div>
                    </div>
                </div>
            </div>
    )
}

export default Footer;