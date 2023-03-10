import React, { Component } from 'react'
import Header from "../../components/Header/Header.js";
import './Admin_UserDetailManagement.css'
import Footer from "../../components/Footer/Footer.js";
import PageTitle from "../../components/PageTitle/PageTitle.js"
import btn_element from '../../resources/btn_element.png'
import Popup from 'reactjs-popup'
// import jwt_decode from 'jwt-decode'
import activated_checkbox from '../../resources/activated_checkbox.png'
import deactivated_checkbox from '../../resources/deactivated_checkbox.png'
import { ClickAwayListener } from '@material-ui/core';

import dropdown_btn from '../../resources/dropdown_icon.png'
import white_dropdown_btn from '../../resources/white_dropdown_icon.png'


class Admin_UserDetailManagement extends Component {
    constructor(props) {
        super(props);

        //for popups
        this.notifyContent = "";

        //for token of admin
        this.token = "";

        //for change pass world
        this.newPassword_ = "";
        this.newPassword_Confirm = "";
        this.canUpdatePass = false;
        this.canClickUpdatePass = false;

        //for update user info
        this.newDisplayName = "";
        this.isDisplayNameOK = false;
        this.newEmail = "";
        this.isEmailOK = false;

        //for fetching information of this user.
        this.user_ID = "";

        //for update remind setting 
        this.canClickUpdateRemindSetting = false;

        this.state = {
            userInfo: {

            },
            userInfo_PatchDTO: {
                "id": "",
                "displayName": null,
                "userName": null,
                "email": null,
                "currentPassword": null,
                "newPassword": null
            },
            remindSetting_PutDTO: {
                "days": -1
            },
            remindOptionsList: [
                {
                    "id": 0,
                    "value": "Don't remind me!",
                    "active": true
                },
                {
                    "id": 1,
                    "value": "Everyday",
                    "active": false
                },
                {
                    "id": 2,
                    "value": "2 days",
                    "active": false
                },
                {
                    "id": 3,
                    "value": "3 days",
                    "active": false
                },
                {
                    "id": 5,
                    "value": "5 days",
                    "active": false
                }
            ],
            "isUpdateInfo": true,
            "isChangePass": false,
            "isChangeRemind": false,
            "avatar_URL": "https://i.pinimg.com/originals/12/e0/4d/12e04d6c2e9c734b3bd841414ad20a1f.png",
            "user_ID": "",
            "password_length": 10,
            "isLoadDone": false,
            remindSetting_PutDTO: {
                "monday": -1,
                "tuesday": -1,
                "wednesday": -1,
                "thursday": -1,
                "friday": -1,
                "saturday": -1,
                "sunday": -1
            },
            remindOptionsList: [
                {
                    mapName: "monday",
                    name: "Monday", curValue: {
                        id: 24,
                        value: "None"
                    }, values: [

                        {
                            id: 0,
                            value: "00:00"
                        }, {
                            id: 1,
                            value: "01:00"
                        },
                        {
                            id: 2,
                            value: "02:00"
                        },
                        {
                            id: 3,
                            value: "03:00"
                        },
                        {
                            id: 4,
                            value: "04:00"
                        },
                        {
                            id: 5,
                            value: "05:00"
                        },
                        {
                            id: 6,
                            value: "06:00"
                        },
                        {
                            id: 7,
                            value: "07:00"
                        },
                        {
                            id: 8,
                            value: "08:00"
                        },
                        {
                            id: 9,
                            value: "09:00"
                        },
                        {
                            id: 10,
                            value: "10:00"
                        },
                        {
                            id: 11,
                            value: "11:00"
                        },
                        {
                            id: 12,
                            value: "12:00"
                        }, {
                            id: 13,
                            value: "13:00"
                        },
                        {
                            id: 14,
                            value: "14:00"
                        },
                        {
                            id: 15,
                            value: "15:00"
                        },
                        {
                            id: 16,
                            value: "16:00"
                        },
                        {
                            id: 17,
                            value: "17:00"
                        },
                        {
                            id: 18,
                            value: "18:00"
                        },
                        {
                            id: 19,
                            value: "19:00"
                        },
                        {
                            id: 20,
                            value: "20:00"
                        },
                        {
                            id: 21,
                            value: "21:00"
                        },
                        {
                            id: 22,
                            value: "22:00"
                        },
                        {
                            id: 23,
                            value: "23:00"
                        }
                    ]
                },
                {
                    mapName: "tuesday",
                    name: "Tuesday", curValue: {
                        id: 24,
                        value: "None"
                    }, values: [

                        {
                            id: 0,
                            value: "00:00"
                        }, {
                            id: 1,
                            value: "01:00"
                        },
                        {
                            id: 2,
                            value: "02:00"
                        },
                        {
                            id: 3,
                            value: "03:00"
                        },
                        {
                            id: 4,
                            value: "04:00"
                        },
                        {
                            id: 5,
                            value: "05:00"
                        },
                        {
                            id: 6,
                            value: "06:00"
                        },
                        {
                            id: 7,
                            value: "07:00"
                        },
                        {
                            id: 8,
                            value: "08:00"
                        },
                        {
                            id: 9,
                            value: "09:00"
                        },
                        {
                            id: 10,
                            value: "10:00"
                        },
                        {
                            id: 11,
                            value: "11:00"
                        },
                        {
                            id: 12,
                            value: "12:00"
                        }, {
                            id: 13,
                            value: "13:00"
                        },
                        {
                            id: 14,
                            value: "14:00"
                        },
                        {
                            id: 15,
                            value: "15:00"
                        },
                        {
                            id: 16,
                            value: "16:00"
                        },
                        {
                            id: 17,
                            value: "17:00"
                        },
                        {
                            id: 18,
                            value: "18:00"
                        },
                        {
                            id: 19,
                            value: "19:00"
                        },
                        {
                            id: 20,
                            value: "20:00"
                        },
                        {
                            id: 21,
                            value: "21:00"
                        },
                        {
                            id: 22,
                            value: "22:00"
                        },
                        {
                            id: 23,
                            value: "23:00"
                        }
                    ]
                },
                {
                    mapName: "wednesday",
                    name: "Wednesday", curValue: {
                        id: 24,
                        value: "None"
                    }, values: [

                        {
                            id: 0,
                            value: "00:00"
                        }, {
                            id: 1,
                            value: "01:00"
                        },
                        {
                            id: 2,
                            value: "02:00"
                        },
                        {
                            id: 3,
                            value: "03:00"
                        },
                        {
                            id: 4,
                            value: "04:00"
                        },
                        {
                            id: 5,
                            value: "05:00"
                        },
                        {
                            id: 6,
                            value: "06:00"
                        },
                        {
                            id: 7,
                            value: "07:00"
                        },
                        {
                            id: 8,
                            value: "08:00"
                        },
                        {
                            id: 9,
                            value: "09:00"
                        },
                        {
                            id: 10,
                            value: "10:00"
                        },
                        {
                            id: 11,
                            value: "11:00"
                        },
                        {
                            id: 12,
                            value: "12:00"
                        }, {
                            id: 13,
                            value: "13:00"
                        },
                        {
                            id: 14,
                            value: "14:00"
                        },
                        {
                            id: 15,
                            value: "15:00"
                        },
                        {
                            id: 16,
                            value: "16:00"
                        },
                        {
                            id: 17,
                            value: "17:00"
                        },
                        {
                            id: 18,
                            value: "18:00"
                        },
                        {
                            id: 19,
                            value: "19:00"
                        },
                        {
                            id: 20,
                            value: "20:00"
                        },
                        {
                            id: 21,
                            value: "21:00"
                        },
                        {
                            id: 22,
                            value: "22:00"
                        },
                        {
                            id: 23,
                            value: "23:00"
                        }
                    ]
                },
                {
                    mapName: "thursday",
                    name: "Thursday", curValue: {
                        id: 24,
                        value: "None"
                    }, values: [

                        {
                            id: 0,
                            value: "00:00"
                        }, {
                            id: 1,
                            value: "01:00"
                        },
                        {
                            id: 2,
                            value: "02:00"
                        },
                        {
                            id: 3,
                            value: "03:00"
                        },
                        {
                            id: 4,
                            value: "04:00"
                        },
                        {
                            id: 5,
                            value: "05:00"
                        },
                        {
                            id: 6,
                            value: "06:00"
                        },
                        {
                            id: 7,
                            value: "07:00"
                        },
                        {
                            id: 8,
                            value: "08:00"
                        },
                        {
                            id: 9,
                            value: "09:00"
                        },
                        {
                            id: 10,
                            value: "10:00"
                        },
                        {
                            id: 11,
                            value: "11:00"
                        },
                        {
                            id: 12,
                            value: "12:00"
                        }, {
                            id: 13,
                            value: "13:00"
                        },
                        {
                            id: 14,
                            value: "14:00"
                        },
                        {
                            id: 15,
                            value: "15:00"
                        },
                        {
                            id: 16,
                            value: "16:00"
                        },
                        {
                            id: 17,
                            value: "17:00"
                        },
                        {
                            id: 18,
                            value: "18:00"
                        },
                        {
                            id: 19,
                            value: "19:00"
                        },
                        {
                            id: 20,
                            value: "20:00"
                        },
                        {
                            id: 21,
                            value: "21:00"
                        },
                        {
                            id: 22,
                            value: "22:00"
                        },
                        {
                            id: 23,
                            value: "23:00"
                        }
                    ]
                },
                {
                    mapName: "friday",
                    name: "Friday", curValue: {
                        id: 24,
                        value: "None"
                    }, values: [

                        {
                            id: 0,
                            value: "00:00"
                        }, {
                            id: 1,
                            value: "01:00"
                        },
                        {
                            id: 2,
                            value: "02:00"
                        },
                        {
                            id: 3,
                            value: "03:00"
                        },
                        {
                            id: 4,
                            value: "04:00"
                        },
                        {
                            id: 5,
                            value: "05:00"
                        },
                        {
                            id: 6,
                            value: "06:00"
                        },
                        {
                            id: 7,
                            value: "07:00"
                        },
                        {
                            id: 8,
                            value: "08:00"
                        },
                        {
                            id: 9,
                            value: "09:00"
                        },
                        {
                            id: 10,
                            value: "10:00"
                        },
                        {
                            id: 11,
                            value: "11:00"
                        },
                        {
                            id: 12,
                            value: "12:00"
                        }, {
                            id: 13,
                            value: "13:00"
                        },
                        {
                            id: 14,
                            value: "14:00"
                        },
                        {
                            id: 15,
                            value: "15:00"
                        },
                        {
                            id: 16,
                            value: "16:00"
                        },
                        {
                            id: 17,
                            value: "17:00"
                        },
                        {
                            id: 18,
                            value: "18:00"
                        },
                        {
                            id: 19,
                            value: "19:00"
                        },
                        {
                            id: 20,
                            value: "20:00"
                        },
                        {
                            id: 21,
                            value: "21:00"
                        },
                        {
                            id: 22,
                            value: "22:00"
                        },
                        {
                            id: 23,
                            value: "23:00"
                        }
                    ]
                },
                {
                    mapName: "saturday",
                    name: "Saturday", curValue: {
                        id: 24,
                        value: "None"
                    }, values: [

                        {
                            id: 0,
                            value: "00:00"
                        }, {
                            id: 1,
                            value: "01:00"
                        },
                        {
                            id: 2,
                            value: "02:00"
                        },
                        {
                            id: 3,
                            value: "03:00"
                        },
                        {
                            id: 4,
                            value: "04:00"
                        },
                        {
                            id: 5,
                            value: "05:00"
                        },
                        {
                            id: 6,
                            value: "06:00"
                        },
                        {
                            id: 7,
                            value: "07:00"
                        },
                        {
                            id: 8,
                            value: "08:00"
                        },
                        {
                            id: 9,
                            value: "09:00"
                        },
                        {
                            id: 10,
                            value: "10:00"
                        },
                        {
                            id: 11,
                            value: "11:00"
                        },
                        {
                            id: 12,
                            value: "12:00"
                        }, {
                            id: 13,
                            value: "13:00"
                        },
                        {
                            id: 14,
                            value: "14:00"
                        },
                        {
                            id: 15,
                            value: "15:00"
                        },
                        {
                            id: 16,
                            value: "16:00"
                        },
                        {
                            id: 17,
                            value: "17:00"
                        },
                        {
                            id: 18,
                            value: "18:00"
                        },
                        {
                            id: 19,
                            value: "19:00"
                        },
                        {
                            id: 20,
                            value: "20:00"
                        },
                        {
                            id: 21,
                            value: "21:00"
                        },
                        {
                            id: 22,
                            value: "22:00"
                        },
                        {
                            id: 23,
                            value: "23:00"
                        }
                    ]
                },
                {
                    mapName: "sunday",
                    name: "Sunday", curValue: {
                        id: 24,
                        value: "None"
                    }, values: [

                        {
                            id: 0,
                            value: "00:00"
                        }, {
                            id: 1,
                            value: "01:00"
                        },
                        {
                            id: 2,
                            value: "02:00"
                        },
                        {
                            id: 3,
                            value: "03:00"
                        },
                        {
                            id: 4,
                            value: "04:00"
                        },
                        {
                            id: 5,
                            value: "05:00"
                        },
                        {
                            id: 6,
                            value: "06:00"
                        },
                        {
                            id: 7,
                            value: "07:00"
                        },
                        {
                            id: 8,
                            value: "08:00"
                        },
                        {
                            id: 9,
                            value: "09:00"
                        },
                        {
                            id: 10,
                            value: "10:00"
                        },
                        {
                            id: 11,
                            value: "11:00"
                        },
                        {
                            id: 12,
                            value: "12:00"
                        }, {
                            id: 13,
                            value: "13:00"
                        },
                        {
                            id: 14,
                            value: "14:00"
                        },
                        {
                            id: 15,
                            value: "15:00"
                        },
                        {
                            id: 16,
                            value: "16:00"
                        },
                        {
                            id: 17,
                            value: "17:00"
                        },
                        {
                            id: 18,
                            value: "18:00"
                        },
                        {
                            id: 19,
                            value: "19:00"
                        },
                        {
                            id: 20,
                            value: "20:00"
                        },
                        {
                            id: 21,
                            value: "21:00"
                        },
                        {
                            id: 22,
                            value: "22:00"
                        },
                        {
                            id: 23,
                            value: "23:00"
                        }
                    ]
                }
            ]
        }
    }

    componentDidMount() {
        this.fetchInfo();
    }

    fetchInfo() {
        //l???y id t??? path parameter
        this.user_ID = this.props.match.params.id;

        //x??? l?? token
        this.token = sessionStorage.getItem('token');

        //Ki???m tra c?? ph???i Admin hay kh??ng?

        fetch('/api/v1/users/' + this.user_ID, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${this.token}`
            }
        })
            .then(response =>
                response.json()
            )
            .then(response => {
                this.setState({
                    userInfo: response,
                    isLoadDone: true
                });

                this.newDisplayName = response.displayName;
                this.newEmail = response.email;
            })
            .catch(error => {
                console.log(error);
            })
    }

    generateHiddenPass() {
        var hidden_pass = "";
        for (let i = 0; i < this.state.password_length; i++) {
            hidden_pass += "*";
        }
        return hidden_pass;
    }

    checkValidNewPassword = () => {
        if (this.newPassword_ === this.newPassword_Confirm) {
            if (this.newPassword_.length <= 5) {
                this.notifyContent = "The length of the new password must be at least 6!";
                this.canUpdatePass = false;
            }
            else {
                this.canUpdatePass = true;
            }
        }
        else {
            this.notifyContent = "New password and confirmation password must be the same!";
            this.canUpdatePass = false;
        }
    }

    checkPasswordEmptyField = () => {
        if (this.newPassword_ === null || this.newPassword_ === ""
            || this.currentPassword === null || this.currentPassword === ""
            || this.newPassword_Confirm === null || this.newPassword_Confirm === "") {
            this.canClickUpdatePass = false;
        }
        else {
            this.canClickUpdatePass = true;
        }
    }

    updateInfo = (e) => {
        e.preventDefault();

        fetch('/api/v1/users/' + this.user_ID, {
            method: "PATCH",
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${this.token}`
            },
            body: JSON.stringify(this.state.userInfo_PatchDTO)
        })
            .then(response => {
                console.log(response);
                if (response.status === 200 || response.status === 204) {
                    this.closeUpdateUserInfoConfirmationPopupHandler();
                    this.notifyContent = "Update info success!";
                    this.openNotifyPopupHandler();
                }
                else {
                    this.closeUpdateUserInfoConfirmationPopupHandler();
                    this.notifyContent = "Update info failed!";
                    this.openNotifyPopupHandler();
                }
            }
            )
            .catch(error => {
                console.log(error);
            })
    }

    updatePassword = (e) => {

        if (!this.canUpdatePass) {
            this.openNormalNotifyPopupHandler();
        }
        else {
            e.preventDefault();
            fetch('/api/v1/users/' + this.user_ID, {
                method: "PATCH",
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${this.token}`
                },
                body: JSON.stringify(this.state.userInfo_PatchDTO)
            })
                .then(response => response.json())
                .then(response => {
                    if (response.currentPassword === null) {
                        this.notifyContent = "Your current password is wrong!";
                        this.openNormalNotifyPopupHandler();
                    }
                    else {
                        this.notifyContent = "Update password success!";
                        this.openNotifyPopupHandler();
                    }
                })
        }
    }


    //for reminder
    fetchReminderInfo = () => {
        this.canClickUpdateRemindSetting = false;
        fetch('/api/v1/users/' + this.user_ID + "/reminders", {
            method: "GET",
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${this.token}`
            }
        })
            .then(
                response => response.json())
            .then((response) => {
                console.log(response);
                //mapping to show 
                this.state.remindOptionsList.map(
                    remindOption => {
                        for (let i = 0; i <= 23; i++) {
                            if (i === response[remindOption.mapName]) {
                                console.log("OK");
                                remindOption.curValue.value = remindOption.values[i].value;
                            }
                        }

                    }
                )
                this.setState({ remindSetting_PutDTO: response });
            })
            .catch(error => {
                console.log(error);
            })
    }

    activateRemindSelection = (remind_option_ID) => {
        this.canClickUpdateRemindSetting = true;
        this.setState({
            remindSetting_PutDTO: {
                days: remind_option_ID
            }
        })
    }

    updateRemindSetting = (e) => {
        e.preventDefault();

        fetch('/api/v1/users/' + this.user_ID + "/reminders", {
            method: "PUT",
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${this.token}`
            },
            body: JSON.stringify(this.state.remindSetting_PutDTO)
        })
            .then(response => {

                if (response.status === 200 || response.status === 204) {
                    this.closeUpdateRemindConfirmationPopupHandler();
                    this.notifyContent = "You have change your remind setting!";
                    this.openNotifyPopupHandler();
                }
                else {
                    this.closeUpdateRemindConfirmationPopupHandler();
                    this.notifyContent = "Error!";
                    this.openNotifyPopupHandler();
                }
            }
            )
            .catch(error => {
                console.log(error);
            })
    }

    checkDisplayNameEmptyField = () => {
        console.log("Display name checked!" + this.newDisplayName + " " + this.newEmail);
        if (this.newDisplayName === "" || this.newDisplayName === null) {
            this.isDisplayNameOK = false;
            console.log("display name: FALSE ");
            return;
        }
        this.isDisplayNameOK = true;
    }

    checkValidateEmail = () => {
        console.log("Email checked!" + this.newDisplayName + " " + this.newEmail);
        const re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        console.log(re.test(String(this.newEmail).toLowerCase()));
        if (!(re.test(String(this.newEmail).toLowerCase()))) {
            this.isEmailOK = false;
            console.log("email: FALSE 1");
            return;
        }
        if (this.newEmail === "" || this.newEmail === null) {
            this.isEmailOK = false;
            console.log("email: FALSE 2");
            return;
        }
        this.isEmailOK = true;
    }

    handleBanUser = (e) => {
        e.preventDefault();
        let token = sessionStorage.getItem('token');
        if (!token || token.length < 10)
            return;

        fetch('/api/v1/users/' + this.user_ID + "/ban?status=1", {
            method: "PUT",
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        })
            .then(response => {
                response.json();
                console.log(response);

                //bug 403 but OK
                this.notifyContent = "Ban user success!";
                this.closeVerifyBannedPopupHandler();
                this.openNotifyPopupHandler();
            })
        // .then(response => {
        //     // console.log(response);
        //     // if (response.currentPassword === null) {
        //     //     this.notifyContent = "Your current password is wrong!";
        //     //     this.openNormalNotifyPopupHandler();
        //     // }
        //     // else {
        //     //     this.notifyContent = "Update password success!";
        //     //     this.openNotifyPopupHandler();
        //     // }
        // })


        console.log('You have banned user have id' + this.props.id);

    }


    handleUnbanUser = (e) => {
        e.stopPropagation();
        e.preventDefault();

        let token = sessionStorage.getItem('token');
        if (!token || token.length < 10)
            return;

        fetch('/api/v1/users/' + this.user_ID + "/ban?status=0", {
            method: "PUT",
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        })
            .then(response => {
                response.json();
                console.log(response);

                //bug 403 but OK
                this.notifyContent = "Unban user success!";
                this.closeVerifyUnbannedPopupHandler();
                this.openNotifyPopupHandler();
            })
        // .then(response => {
        //     // if (response.currentPassword === null) {
        //     //     this.notifyContent = "Your current password is wrong!";
        //     //     this.openNormalNotifyPopupHandler();
        //     // }
        //     // else {
        //     //     this.notifyContent = "Update password success!";
        //     //     this.openNotifyPopupHandler();
        //     // }
        // })


        console.log('You have banned user have id' + this.props.id);
    }

    render() {
        let view;

        let remindOptionsList = this.state.remindOptionsList.map(remindOption =>
            <div style={{ display: "flex", marginBottom: "10px" }} key={remindOption.name}>
                <div className="Simple_Label" style={{ height: "25px" }}>
                    {remindOption.name}
                </div>
                <div style={{ width: "10px" }} ></div>
                {
                    <ClickAwayListener onClickAway={(e) => { this.closeAllChangeRoleDropdownCombobox(e, "user-role-parent-dropdown-combobox-" + remindOption.name, "user-role-parent-dropdown-combobox-text-" + remindOption.name, "user-role-dropdown-btn-element-" + remindOption.name, "user-role-dropdown-combobox-container-" + remindOption.name) }}>
                        <div style={{ position: "relative", display: "flex", width: "100%" }}>
                            <div style={{ position: "relative", display: "flex", justifyContent: "flex-end", width: "100%" }}>
                                <div style={{ width: "140px" }}>
                                    <div className="Parent_Dropdown_Combobox" id={"user-role-parent-dropdown-combobox-" + remindOption.name}
                                        onClick={(e) => this.handleDropDownMenuClick(e, "user-role-parent-dropdown-combobox-" + remindOption.name, "user-role-parent-dropdown-combobox-text-" + remindOption.name, "user-role-dropdown-btn-element-" + remindOption.name, "user-role-dropdown-combobox-container-" + remindOption.name)}>
                                        <div style={{ display: "flex", zIndex: 1 }}>
                                            <div className="Vertical_Menu_Item_Text" id={"user-role-parent-dropdown-combobox-text-" + remindOption.name}>
                                                {remindOption.curValue.value}
                                            </div>
                                        </div>
                                        <img alt="v" className="Dropdown_Btn_Element" src={dropdown_btn} id={"user-role-dropdown-btn-element-" + remindOption.name} />
                                    </div>

                                    {/* {this.isAnyChangeRoleDropdownComboboxOpen ? ( */}
                                    <div className="Dropdown_Combobox_Container" style={{ position: "absolute", zIndex: 2, maxHeight: "240px", overflow: "auto" }} id={"user-role-dropdown-combobox-container-" + remindOption.name}>
                                        {
                                            remindOption.values.map(detailRemindOption =>
                                                remindOption.curValue.id === detailRemindOption.id ?
                                                    <div className="Activated_Dropdown_Combobox_Sub_Item" id={"user-role-dropdown-combobox-sub-item-" + remindOption.name + detailRemindOption.id} value={detailRemindOption.value} key={detailRemindOption.id}>{detailRemindOption.value}</div>
                                                    :
                                                    <div className="Dropdown_Combobox_Sub_Item" id={"user-role-dropdown-combobox-sub-item-" + remindOption.name + detailRemindOption.id} value={detailRemindOption.value} key={detailRemindOption.id}
                                                        onClick={() => this.handleDropDownMenuItemClick(remindOption.name, detailRemindOption.id, detailRemindOption.value)}>
                                                        {detailRemindOption.value}
                                                    </div>
                                            )}

                                        <div style={{ marginBottom: "10px" }} />
                                    </div>
                                    {/* ) :  */}
                                    {/* <div id={"user-role-dropdown-combobox-container-" + remindOption.name}></div>} */}

                                </div>
                            </div>
                        </div>
                    </ClickAwayListener>

                }

            </div>
        )

        if (this.state.isUpdateInfo) {
            view = <div className="User_Show_Info_Port">
                <div className="User_Show_Info_Sub_Port">
                    <div >
                        <div className="Label">Display name:</div>
                        <input className="Changable_Input" type="text" defaultValue={this.state.userInfo.displayName} onChange={this.changeDisplayNameHandler}></input>
                    </div>
                    <div >
                        <div className="Label">Username:</div>
                        <input className="Unchangable_Input" type="text" defaultValue={this.state.userInfo.userName} readOnly></input>
                    </div>
                    <div>
                        <div className="Label">Email:</div>
                        <input className="Changable_Input" type="text" defaultValue={this.state.userInfo.email} onChange={this.changeEmailHandler}></input>
                    </div>
                    <div >
                        <div className="Label">Passwords:</div>
                        <input className="Unchangable_Input" type="text" defaultValue={this.generateHiddenPass()} readOnly ></input>
                    </div>
                    <div className="Save_Change_Info_Btn_Port" >
                        <button className="Blue_Button" disabled={(!(this.isDisplayNameOK) || !(this.isEmailOK))} onClick={() => { this.notifyContent = "Do you want to update your information?"; this.openUpdateUserInfoConfirmationPopupHandler() }}>Save changes</button>
                    </div>
                </div>
            </div >
        }
        else {
            if (this.state.isChangePass) {
                view =
                    <div className="User_Show_Info_Port">
                        <div className="User_Show_Info_Sub_Port">
                            <div>
                                <div className="Simple_Label">Current passwords:</div>
                                <input className="Simple_Changable_Text_Input" type="password" defaultValue="" onChange={this.changeCurrentPasswordHandler} ></input>
                            </div>
                            <div>
                                <div className="Simple_Label">New passwords:</div>
                                <input className="Simple_Changable_Text_Input" type="password" defaultValue="" onChange={this.changeNewPasswordHandler}></input>
                            </div>
                            <div>
                                <div className="Label">Confirm new passwords:</div>
                                <input className="Simple_Changable_Text_Input" type="password" defaultValue="" onChange={this.changeConfirmNewPasswordHandler}></input>
                            </div>
                            <div className="Save_Change_Info_Btn_Port">
                                <button className="Blue_Button" disabled={!this.canClickUpdatePass} onClick={this.updatePassword}>Save password</button>
                            </div>
                        </div>
                    </div>;
            }
            else
                view =
                    view =
                    <div className="User_Show_Info_Port">
                        <div className="User_Show_Info_Sub_Port">
                            <div className="Remind_Port">
                                Choose a time step and we will announce you via mail.
                        </div>
                            {remindOptionsList}
                            <div className="Save_Change_Remind_Btn_Port">
                                <button className="Blue_Button" onClick={() => { this.notifyContent = "Do you want to change your remind setting?"; this.openUpdateRemindConfirmationPopupHandler() }}>Save setting</button>
                            </div>
                        </div>
                    </div>;
        }

        return (
            <div className="User_Account_Management">
                {/* Header */}
                <div className="User_Account_Management_Header">
                    <Header></Header>
                </div>

                {/* Notification below header */}
               
                <div className="User_Account_Management_Main_Port"> {/* width = 100%, contain all content */}
                    <div className="User_Account_Management_Inline_Port">
                        <div> <PageTitle prevTitle="Edit your" mainTitle="Profile"></PageTitle></div>
                        <div className="User_Menu">
                            {/* User menu + user info port */}
                            <div className="User_Menu_User_Info_Port">

                                {/* Show info of account */}
                                <div className="User_Info_Port">
                                    <div className="Avatar_Port">
                                        <img alt="avatar" className="Avatar" src={this.state.avatar_URL} />
                                    </div>
                                    <div className="User_Name_Gmail_Port">
                                        <div className="User_Name">
                                            {this.state.userInfo.displayName}
                                        </div>
                                        <div className="Gmail">
                                            {this.state.userInfo.email}
                                        </div>
                                        <div className="Ban_Btn_Port">
                                            {console.log(this.state.userInfo.isAccountEnabled)}
                                            {this.state.userInfo.isAccountEnabled ?
                                                <button className="Red_Button" onClick={this.openVerifyBannedPopupHandler}>Ban</button>
                                                : <button className="Red_Button" style={{ background: "#3afe3a" }} onClick={this.openVerifyUnbannedPopupHandler}>Unban</button>
                                            }

                                        </div>
                                    </div>

                                </div>
                                {/* User Menu*/}
                                <div className="User_Menu_Port" hidden={!this.state.isLoadDone} >
                                    <div className="Menu_Item" onClick={this.handleUpdate}>
                                        <img alt="*" className="Btn_Element" src={btn_element}></img>
                                        <div> Update infomation</div>
                                    </div>

                                    <div className="Menu_Item" onClick={this.handleChangePass}>
                                        <img alt="*" className="Btn_Element" src={btn_element}></img>
                                        <div>Change password</div>
                                    </div>
                                    <div className="Menu_Item" onClick={this.handleChangeRemind} >
                                        <img alt="*" className="Btn_Element" src={btn_element}></img>
                                        <div > Remind Setting</div>
                                    </div>
                                </div>

                            </div>
                            {/* Show content of menu item */}
                            {view}
                        </div>
                    </div>
                </div>

                {/* Notify Popup with reload*/}
                <Popup modal
                    open={this.state.isNotifyPopupOpen}
                    onOpen={this.openNotifyPopupHandler}
                    closeOnDocumentClick={false}

                >
                    <React.Fragment>
                        <div className="Align_Center">
                            <div className="Height_30px"></div>
                            <div className="Simple_Label">{this.notifyContent}</div>
                            <div className="Height_30px"></div>
                            <div className="Justify_Content_Space_Between">
                                <button className="Blue_Button" onClick={this.closeNotifyPopupHandlerAndReload}>
                                    OK
                                </button>
                            </div>
                            <div className="Height_10px"></div>
                        </div>
                    </React.Fragment>
                </Popup>

                {/* Notify Popup without reload*/}
                <Popup modal
                    open={this.state.isNormalNotifyPopupOpen}
                    onOpen={this.openNormalNotifyPopupHandler}
                    closeOnDocumentClick={false}
                >
                    <React.Fragment>
                        <div className="Align_Center">
                            <div className="Height_30px"></div>
                            <div className="Simple_Label">{this.notifyContent}</div>
                            <div className="Height_30px"></div>
                            <div className="Justify_Content_Space_Between">
                                <button className="Blue_Button" onClick={this.closeNormalNotifyPopupHandler}>
                                    OK
                                </button>
                            </div>
                            <div className="Height_10px"></div>
                        </div>
                    </React.Fragment>
                </Popup>

                {/* Confirm update user info Popup*/}
                <Popup modal
                    open={this.state.isUpdateUserInfoConfirmationPopupOpen}
                    onOpen={this.openUpdateUserInfoConfirmationPopupHandler}
                    closeOnDocumentClick={false}
                >
                    <React.Fragment>
                        <div className="Align_Center">
                            <div className="Height_30px"></div>
                            <div className="Simple_Label">{this.notifyContent}</div>
                            <div className="Height_30px"></div>
                            <div className="Justify_Content_Space_Between">
                                <button className="Blue_Button" onClick={(e) => this.updateInfo(e)}>
                                    Verify
                                </button>
                                <button className="Red_Button" onClick={this.closeUpdateUserInfoConfirmationPopupHandler}>
                                    Cancel
                                </button>
                            </div>
                            <div className="Height_10px"></div>
                        </div>
                    </React.Fragment>
                </Popup>

                {/* Confirm ban user Popup*/}
                <Popup modal
                    open={this.state.isBanUserConfirmationPopupOpen}
                    onOpen={this.openBanUserConfirmationPopupHandler}
                    closeOnDocumentClick={false}
                >
                    <React.Fragment>
                        <div className="Align_Center">
                            <div className="Height_30px"></div>
                            <div className="Simple_Label">{this.notifyContent}</div>
                            <div className="Height_30px"></div>
                            <div className="Justify_Content_Space_Between">
                                <button className="Blue_Button" onClick={(e) => this.banUser(e)}>
                                    Verify
                                </button>
                                <button className="Red_Button" onClick={this.closeUpdateUserInfoConfirmationPopupHandler}>
                                    Cancel
                                </button>
                            </div>
                            <div className="Height_10px"></div>
                        </div>
                    </React.Fragment>
                </Popup>

                {/* Update remind popup */}
                <Popup modal
                    open={this.state.isUpdateRemindConfirmationPopupOpen}
                    onOpen={this.openUpdateRemindConfirmationPopupHandler}
                    closeOnDocumentClick={false}
                >
                    <React.Fragment>
                        <div className="Align_Center">
                            <div className="Height_30px"></div>
                            <div className="Simple_Label">{this.notifyContent}</div>
                            <div className="Height_30px"></div>
                            <div className="Justify_Content_Space_Between">
                                <button className="Blue_Button" onClick={(e) => this.updateRemindSetting(e)}>
                                    Verify
                                </button>
                                <button className="Red_Button" onClick={this.closeUpdateRemindConfirmationPopupHandler}>
                                    Cancel
                                </button>
                            </div>
                            <div className="Height_10px"></div>
                        </div>
                    </React.Fragment>
                </Popup>
                {/* Confirm Banned info Popup*/}
                <Popup modal
                    open={this.state.isVerifyBannedPopupOpen}
                    onOpen={this.openVerifyBannedPopupHandler}
                    closeOnDocumentClick={false}
                >
                    <React.Fragment>
                        <div className="Align_Center">
                            <div className="Height_30px"></div>
                            <div className="Simple_Label">{this.notifyContent}</div>
                            <div className="Height_30px"></div>
                            <div className="Justify_Content_Space_Between">
                                <button className="Blue_Button" onClick={(e) => this.handleBanUser(e)}>
                                    Verify
                                </button>
                                <button className="Red_Button" onClick={this.closeVerifyBannedPopupHandler}>
                                    Cancel
                                </button>
                            </div>
                            <div className="Height_10px"></div>
                        </div>
                    </React.Fragment>
                </Popup>

                {/* Confirm Unban info Popup*/}
                <Popup modal
                    open={this.state.isVerifyUnbannedPopupOpen}
                    onOpen={this.openVerifyUnbanPopupHandler}
                    closeOnDocumentClick={false}
                >
                    <React.Fragment>
                        <div className="Align_Center">
                            <div className="Height_30px"></div>
                            <div className="Simple_Label">{this.notifyContent}</div>
                            <div className="Height_30px"></div>
                            <div className="Justify_Content_Space_Between">
                                <button className="Blue_Button" onClick={(e) => this.handleUnbanUser(e)}>
                                    Verify
                                </button>
                                <button className="Red_Button" onClick={this.closeVerifyUnbannedPopupHandler}>
                                    Cancel
                                </button>
                            </div>
                            <div className="Height_10px"></div>
                        </div>
                    </React.Fragment>

                </Popup>
                <div className="User_Account_Management_Footer">
                    <Footer ></Footer>
                </div>
            </div>
        );
    }

    //handle change option
    handleUpdate = () => {
        this.setState({
            "isUpdateInfo": true,
            "isChangePass": false,
            "isChangeRemind ": false,
        })
    }

    handleChangePass = () => {
        this.setState({
            "isUpdateInfo": false,
            "isChangePass": true,
            "isChangeRemind ": false,
        })
    }

    handleChangeRemind = () => {
        this.fetchReminderInfo();
        this.setState({
            "isUpdateInfo": false,
            "isChangePass": false,
            "isChangeRemind ": true,
        })
    }

    //handle change info
    changeDisplayNameHandler = (e) => {
        this.state.userInfo_PatchDTO.displayName = e.target.value;
        this.newDisplayName = e.target.value;
        this.checkDisplayNameEmptyField();
        this.checkValidateEmail();
        this.setState(this.state);
    }

    changeEmailHandler = (e) => {
        this.state.userInfo_PatchDTO.email = e.target.value;
        this.newEmail = e.target.value;
        // console.log(this.newEmail);
        this.checkValidateEmail();
        this.checkDisplayNameEmptyField();
        this.setState(this.state);
    }

    //handle popup:
    openNotifyPopupHandler = () => {
        this.setState({ isNotifyPopupOpen: true });
    }

    openNormalNotifyPopupHandler = () => {
        this.setState({ isNormalNotifyPopupOpen: true });
    }

    closeNormalNotifyPopupHandler = () => {
        this.setState({ isNormalNotifyPopupOpen: false });
    }

    closeNotifyPopupHandlerAndReload = () => {
        this.setState({ isNotifyPopupOpen: false });
        window.location.reload();
    }

    changeCurrentPasswordHandler = (e) => {
        this.state.userInfo_PatchDTO.currentPassword = e.target.value;
        this.checkPasswordEmptyField();
        this.setState(this.state);
    }

    changeNewPasswordHandler = (e) => {
        this.state.userInfo_PatchDTO.newPassword = e.target.value;
        this.newPassword_ = e.target.value;
        this.checkValidNewPassword();
        this.checkPasswordEmptyField();
        this.setState(this.state);
    }

    changeConfirmNewPasswordHandler = (e) => {
        this.state.userInfo_PatchDTO.newPassword = e.target.value;
        this.newPassword_Confirm = e.target.value;
        this.checkValidNewPassword();
        this.checkPasswordEmptyField();
        this.setState(this.state);
    }

    openUpdateUserInfoConfirmationPopupHandler = () => {
        this.setState({ isUpdateUserInfoConfirmationPopupOpen: true });
    }

    closeUpdateUserInfoConfirmationPopupHandler = () => {
        this.setState({ isUpdateUserInfoConfirmationPopupOpen: false });
    }

    openBanUserConfirmationPopupHandler = () => {
        this.setState({ isBanUserConfirmationPopupOpen: true });
    }

    closeBanUserConfirmationPopupHandler = () => {
        this.setState({ isBanUserConfirmationPopupOpen: false });
    }

    openUpdateRemindConfirmationPopupHandler = () => {
        this.setState({ isUpdateRemindConfirmationPopupOpen: true });
    }

    closeUpdateRemindConfirmationPopupHandler = () => {
        this.setState({ isUpdateRemindConfirmationPopupOpen: false });
    }

    //verify ban and unban popup:
    openVerifyBannedPopupHandler = () => {
        this.notifyContent = "Do you want to ban this user?";
        this.state.isVerifyBannedPopupOpen = true;
        this.setState(this.state);
    }

    closeVerifyBannedPopupHandler = () => {
        this.state.isVerifyBannedPopupOpen = false;
        this.setState(this.state);
    }

    openVerifyUnbannedPopupHandler = () => {
        this.notifyContent = "Do you want to UNBAN this user?";
        this.state.isVerifyUnbannedPopupOpen = true;
        this.setState(this.state);
    }

    closeVerifyUnbannedPopupHandler = () => {
        this.state.isVerifyUnbannedPopupOpen = false;
        this.setState(this.state);
    }


    handleDropDownMenuClick = (e, parent_id, show_text_id, dropdown_element_id, container_id) => {
        e.preventDefault();

        let parent_menu_item = document.getElementById(parent_id);
        let dropdown_element = document.getElementById(dropdown_element_id);
        let show_text = document.getElementById(show_text_id);
        let dropdown_container = document.getElementById(container_id);

        if (dropdown_container.style.display === "block") {
            dropdown_container.style.display = "none";
            parent_menu_item.style.background = "white";
            parent_menu_item.style.paddingLeft = "0px";
            show_text.style.color = "#363636";
            dropdown_element.src = dropdown_btn;
        }
        else {
            parent_menu_item.style.background = "#5279DB"
            dropdown_container.style.display = "block";
            parent_menu_item.style.paddingLeft = "10px";
            show_text.style.color = "white";
            dropdown_element.src = white_dropdown_btn;
        }

        this.isAnyChangeRoleDropdownComboboxOpen = true;
        this.setState({});
    }

    handleDropDownMenuItemClick = (remindOptionName, detailRemindOptionID, detailRemindOptionValue) => {
        //change current UI
        let item_id = "user-role-dropdown-combobox-sub-item-" + remindOptionName + detailRemindOptionID;
        let sub_dropdown_item = document.getElementById(item_id);

        for (let i = 0; i <= 23; i++) {
            let sub_dropdown_item_index_id = "user-role-dropdown-combobox-sub-item-" + remindOptionName + i;
            let sub_dropdown_item_index = document.getElementById(sub_dropdown_item_index_id);
            sub_dropdown_item_index.className = "Dropdown_Combobox_Sub_Item";
        }

        sub_dropdown_item.className = "Activated_Dropdown_Combobox_Sub_Item";

        this.state.remindOptionsList.map(
            remindOption => {
                if (remindOption.name === remindOptionName) {
                    remindOption.curValue.value = detailRemindOptionValue;

                }
            }
        )

        //close combobox
        let parent_id = "user-role-parent-dropdown-combobox-" + remindOptionName;
        let show_text_id = "user-role-parent-dropdown-combobox-text-" + remindOptionName;
        let dropdown_element_id = "user-role-dropdown-btn-element-" + remindOptionName;
        let container_id = "user-role-dropdown-combobox-container-" + remindOptionName;

        let parent_menu_item = document.getElementById(parent_id);
        let dropdown_element = document.getElementById(dropdown_element_id);
        let show_text = document.getElementById(show_text_id);
        let dropdown_container = document.getElementById(container_id);

        if (dropdown_container.style.display === "block") {
            dropdown_container.style.display = "none";
            parent_menu_item.style.background = "white";
            parent_menu_item.style.paddingLeft = "0px";
            show_text.style.color = "#363636";
            dropdown_element.src = dropdown_btn;
        }

        this.setState({});
        if (remindOptionName === "Monday")
            this.state.remindSetting_PutDTO['monday'] = detailRemindOptionID;
        if (remindOptionName === "Tuesday")
            this.state.remindSetting_PutDTO['tuesday'] = detailRemindOptionID;
        if (remindOptionName === "Wednesday")
            this.state.remindSetting_PutDTO['wednesday'] = detailRemindOptionID;
        if (remindOptionName === "Thursday")
            this.state.remindSetting_PutDTO['thursday'] = detailRemindOptionID;
        if (remindOptionName === "Friday")
            this.state.remindSetting_PutDTO['friday'] = detailRemindOptionID;
        if (remindOptionName === "Saturday")
            this.state.remindSetting_PutDTO['saturday'] = detailRemindOptionID;
        if (remindOptionName === "Sunday")
            this.state.remindSetting_PutDTO['sunday'] = detailRemindOptionID;


    }



    closeAllChangeRoleDropdownCombobox = (e, parent_id, show_text_id, dropdown_element_id, container_id) => {

        let parent_menu_item = document.getElementById(parent_id);
        let dropdown_element = document.getElementById(dropdown_element_id);
        let show_text = document.getElementById(show_text_id);
        let dropdown_container = document.getElementById(container_id);

        if (dropdown_container.style.display === "block") {
            dropdown_container.style.display = "none";
            parent_menu_item.style.background = "white";
            parent_menu_item.style.paddingLeft = "0px";
            show_text.style.color = "#363636";
            dropdown_element.src = dropdown_btn;
        }

        this.setState({})
    }
}

export default Admin_UserDetailManagement;
