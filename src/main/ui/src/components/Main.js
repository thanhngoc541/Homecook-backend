import React from 'react'
import { Switch, Route, Redirect, withRouter } from "react-router-dom"
import { TransitionGroup, CSSTransition } from "react-transition-group"
import Header from './Header'
import Footer from './Footer'

function Main(props){
    return (
        <div>
            <Header />
            <TransitionGroup>
                <CSSTransition>
                    <Switch>
                        <Route />
                        <Redirect to ='/home' />
                    </Switch>
                </CSSTransition>
            </TransitionGroup>
            <Footer />
        </div>
    )
}

export default withRouter(Main)