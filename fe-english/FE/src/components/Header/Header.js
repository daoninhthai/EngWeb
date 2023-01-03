import React, { Component } from "react";
import './Header.css'
import mainLogo from '../../resources/logo.png'
import profilIcon from '../../resources/account_icon.png'
import { Redirect, withRouter } from "react-router-dom";
import { isLogin, isAdmin } from "../../pages/Login/Login.js";


class Header extends Component{
    constructor(props){
        super(props);
    }

    handleLogOut = ()=>{
        sessionStorage.removeItem('token');
        console.log('logout')
        this.setState({})
    }



    render(){
        const isAuthenticated = isLogin();
        let isAdminAccount = isAdmin();
        console.log("admin: " + isAdminAccount);
        let loginOrLogOut;
        if(!isAuthenticated)
            loginOrLogOut = <a href='/login'>Log In</a> ;
        else 
            loginOrLogOut = <a href="/" onClick={this.handleLogOut}>Log Out</a>;
        return(
            <header className="Header_Wrapper">
            <div className="Header">
                <nav className="Header_Navigation">
                    <div className="Header_Logo">
                        <a href="/">
                            <img className="" src={mainLogo}></img>
                        </a>
                    </div>
                    {/* <div className="Header_Vertical_Line"></div> */}
                    <div className="Header_Row header-left">
                        {/* <div className="Header_Horizontal_Line"></div> */}
                        <div className="Header_Item">
                            <ul className="Item_LearnEnglish item-menu">
                                <a href="/">HOME</a>
                            </ul>
                            <ul className="Item_Vocabulary item-menu">
                                <a href="/vocabCategories">VOCABULARY</a>
                            </ul>
                            <ul className="Item_Grammar item-menu">
                                <a href="/grammar">GRAMMAR</a>
                            </ul>
                            <ul className="Item_Chat item-menu">
                                <a href="/chat">ROOM CHAT</a>
                                
                            </ul>
                            
                        </div>
                    </div>
                    <div className="Header_Row user">
                        <div className="Header_Item">
                    {isAdminAccount ? <ul className="Item_ item-menu "><a href="/admin">MANAGE</a></ul> : null}
                            <ul className="Item_Profile_Picture item-menu">
                             
                                <div className="Item_Profile_Picture_Dropdown ">
                                    <a href="/user/">Profile</a>
                                    
                                </div>
                                <div className="login">{loginOrLogOut}</div>
                                
                            </ul></div>
                    </div>
                </nav>
            </div>

          

            </header>
        );
    }
}
export default withRouter(Header);