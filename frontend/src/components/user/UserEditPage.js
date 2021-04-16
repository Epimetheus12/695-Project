import React, { Component, Fragment } from 'react';
import { NavLink } from 'react-router-dom';
import { userService } from '../../infrastructure';
import { toast } from 'react-toastify';
import { ToastComponent } from '../common';
import '../../styles/FormPages.css';

import { connect } from 'react-redux';
import { updateUserAction, changeCurrentTimeLineUserAction, changeAllFriendsAction } from '../../store/actions/userActions';
import { changeAllPicturesAction } from '../../store/actions/pictureActions';
import Input from "@material-ui/core/Input";
import {IconButton, InputAdornment} from "@material-ui/core";
import VisibilityOff from "@material-ui/icons/VisibilityOff";
import Visibility from "@material-ui/icons/Visibility";
import DatePicker from "react-date-picker";
import "react-date-picker/dist/DatePicker.css";
import "react-calendar/dist/Calendar.css";


class UserEditPage extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.timeLineUserData.id,
            username: this.props.timeLineUserData.username,
            email: this.props.timeLineUserData.email,
            firstName: this.props.timeLineUserData.firstName,
            lastName: this.props.timeLineUserData.lastName,
            address: this.props.timeLineUserData.address,
            city: this.props.timeLineUserData.city,
            picURl: this.props.timeLineUserData.picURl,
            backgroundImageUrl: this.props.timeLineUserData.backgroundImageUrl,
            password:this.props.timeLineUserData.password,
            nickname:this.props.timeLineUserData.nickname,
            maritalStatus:this.props.timeLineUserData.maritalStatus,
            gender:this.props.timeLineUserData.gender,
            birthday:this.props.timeLineUserData.birthday,
            touched: {
                username: false,
                email: false,
                firstName: false,
                lastName: false,
                address: false,
                city: false,
                picURl: false,
                backgroundImageUrl: false,
                password: false,
                nickname: false,
                maritalStatus: false,
                gender:false,
                birthday:false,
            }
        }

        this.onChangeHandler = this.onChangeHandler.bind(this);
        this.onSubmitHandler = this.onSubmitHandler.bind(this);
        this.onDateChangeHandler = this.onDateChangeHandler.bind(this);
        this.onShowPassword = this.onShowPassword.bind(this);
    }

    componentDidMount = () => {
        const currentTimeLineUserId = this.props.match.params.id
        if (currentTimeLineUserId !== this.props.timeLineUserData.id) {
            this.props.changeTimeLineUser(currentTimeLineUserId);
            this.props.changeAllPictures(currentTimeLineUserId);
            this.props.changeAllFriends(currentTimeLineUserId);
        }
    }

    componentDidUpdate(prevProps, prevState) {
        const loading = this.props.changeTimeLineUserData.loading ||
            this.props.changeAllFriends.loading || this.props.changePicture.loading;

        if (!loading && this.props.timeLineUserData.id !== this.state.id) {
            this.setState({
                id: this.props.timeLineUserData.id,
                username: this.props.timeLineUserData.username,
                email: this.props.timeLineUserData.email,
                firstName: this.props.timeLineUserData.firstName,
                lastName: this.props.timeLineUserData.lastName,
                address: this.props.timeLineUserData.address,
                city: this.props.timeLineUserData.city,
                picURl: this.props.timeLineUserData.picURl,
                backgroundImageUrl: this.props.timeLineUserData.backgroundImageUrl,
                password:this.props.timeLineUserData.password,
                nickname:this.props.timeLineUserData.nickname,
                maritalStatus:this.props.timeLineUserData.maritalStatus,
                gender:this.props.timeLineUserData.gender,
                birthday:this.props.timeLineUserData.birthday,
            })
        }

        const errorMessage = this.getErrorMessage(prevProps);
        const successMessage = this.getSuccessMessage(prevProps)

        if (errorMessage) {
            toast.error(<ToastComponent.errorToast text={errorMessage} />, {
                position: toast.POSITION.TOP_RIGHT
            });

            this.setState({
                id: this.props.timeLineUserData.id,
                username: this.props.timeLineUserData.username,
                email: this.props.timeLineUserData.email,
                firstName: this.props.timeLineUserData.firstName,
                lastName: this.props.timeLineUserData.lastName,
                address: this.props.timeLineUserData.address,
                city: this.props.timeLineUserData.city,
                picURl: this.props.timeLineUserData.picURl,
                backgroundImageUrl: this.props.timeLineUserData.backgroundImageUrl,
                password:this.props.timeLineUserData.password,
                nickname:this.props.timeLineUserData.nickname,
                maritalStatus:this.props.timeLineUserData.maritalStatus,
                gender: this.props.timeLineUserData.gender,
                birthday:this.props.timeLineUserData.birthday,
            })
        } else if (successMessage) {
            toast.success(<ToastComponent.successToast text={successMessage} />, {
                position: toast.POSITION.TOP_RIGHT
            });
            this.props.history.push(`/home/profile/${this.state.id}`);
        }
    }

    getSuccessMessage(prevProps) {
        if (!this.props.updateUserData.hasError && this.props.updateUserData.message && this.props.updateUserData !== prevProps.updateUserData) {
            return this.props.updateUserData.message;
        }
        return null;
    }

    getErrorMessage(prevProps) {
        if (this.props.updateUserData.hasError && prevProps.updateUserData.error !== this.props.updateUserData.error) {
            return this.props.updateUserData.message || 'Server Error';
        }
        return null;
    }

    onChangeHandler(event) {
        this.setState({
            [event.target.name]: event.target.value
        });
    }

    onShowPassword() {
        this.setState({"touched":{hiddenPass: !this.state.touched.hiddenPass}})
    }

    onDateChangeHandler(date) {
        console.log("===========test=========" + new Date(date).toUTCString())
        const dateString = new Date(date).toUTCString()
        this.setState({
            ['birthday']: date
        });
    }

    onSubmitHandler(event) {
        event.preventDefault();

        if (!this.canBeSubmitted()) {
            return;
        }

        const { touched, ...otherProps } = this.state;
        const loggedInUserId = this.props.loggedInUserData.id;
        this.props.updateUser(loggedInUserId, otherProps);
    }

    canBeSubmitted() {
        const { username, email, firstName, lastName, address, city, picURl, backgroundImageUrl,password,nickname,maritalStatus,gender,birthday } = this.state;
        const errors = this.validate(username, email, firstName, lastName, address, city, picURl, backgroundImageUrl,password,nickname,maritalStatus,gender,birthday)
        const isDisabled = Object.keys(errors).some(x => errors[x])
        return !isDisabled;
    }

    handleBlur = (field) => (event) => {
        this.setState({
            touched: { ...this.state.touched, [field]: true }
        });
    }

    validate = (username, email, firstName, lastName, address, city, picURl, backgroundImageUrl,password,nickname,maritalStatus,gender,birthday) => {
        const emailRegex = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$/;
        const firstLastNameRegex = /^[A-Z]([a-zA-Z]+)?$/;
        const passwordRegex = /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[.,!@#$%^&+=])(?=\S+$).{8,30}$/;
        const nicknameRegex = /^([0-9a-zA-Z]+)?$/;
        let testEmail = emailRegex.test(email)
        let testFirstName = firstLastNameRegex.test(firstName)
        let testLastName = firstLastNameRegex.test(lastName)
        let testPassword = passwordRegex.test(password)
        let testNickname = nicknameRegex.test(nickname)
        const testBirthday = isNaN(new Date(birthday).getFullYear())

        return {
            username: username.length < 4 || username.length > 16,
            email: email.length === 0 || !testEmail,
            firstName: firstName.length === 0 || !testFirstName,
            lastName: lastName.length === 0 || !testLastName,
            address: address.length === 0,
            city: city.length === 0,
            picURl: picURl.length === 0,
            backgroundImageUrl: backgroundImageUrl.length === 0,
            password: password.length < 8 || password.length > 30 || !testPassword,
            nickname: nickname.length < 4 || nickname.length > 16 || !testNickname,
            maritalStatus: maritalStatus.length===0,
            gender: gender.length === 0,
            birthday: testBirthday,
        }
    }

    render() {
        const { username, email, firstName, lastName, address, city, picURl, backgroundImageUrl,password,nickname,maritalStatus,gender,birthday } = this.state;

        const loggedInUserName = userService.getUsername();
        const loggedInRole = userService.getRole();
        const isAdmin = userService.isAdmin();
        const isRoot = userService.isRoot();

        let showPicsButtons = true;
        if (loggedInUserName !== username && (loggedInRole !== "ROOT")) {
            showPicsButtons = false;
        }
        const errors = this.validate(username, email, firstName, lastName, address, city, picURl, backgroundImageUrl,password,nickname,maritalStatus,gender,birthday);
        const isEnabled = !Object.keys(errors).some(x => errors[x])

        const shouldMarkError = (field) => {
            const hasError = errors[field];
            const shouldShow = this.state.touched[field];

            return hasError ? shouldShow : false;
        }
        return (
            <Fragment>
                <article className="main-article-shared-content">
                    <section className="form-content-section">
                        <div className="container mb-4">
                            <h1 className="text-center font-weight-bold mt-4" style={{ 'margin': '1rem auto' }}>Edit Account</h1>

                            <div className="hr-styles"></div>

                            <form className="Register-form-container  " onSubmit={this.onSubmitHandler} >

                                <div className="section-container w-100 mx-auto text-center">
                                    <section className="left-section">
                            {/*            { <div className="form-group">*/}
                            {/*    <label htmlFor="username" className="font-weight-bold" >Username</label>*/}
                            {/*    <input*/}
                            {/*        type="text"*/}
                            {/*        className={"form-control " + (shouldMarkError('username') ? "error" : "")}*/}
                            {/*        id="username"*/}
                            {/*        name="username"*/}
                            {/*        value={this.state.username}*/}
                            {/*        onChange={this.onChangeHandler}*/}
                            {/*        onBlur={this.handleBlur('username')}*/}
                            {/*        aria-describedby="usernameHelp"*/}
                            {/*        placeholder="Enter username"*/}
                            {/*    />*/}
                            {/*    {shouldMarkError('username') && <small id="usernameHelp" className="form-text alert alert-danger"> {(!this.state.username ? 'Username is required!' : 'Username should be at least 4 and maximum 16 characters long!')}</small>}*/}
                            {/*</div> }*/}
                                        <div className="form-group">
                                            <label htmlFor="nickname" >Nickname</label>
                                            <input
                                                type="text"
                                                className={"form-control " + (shouldMarkError('nickname') ? "error" : "")}
                                                id="nickname"
                                                name="nickname"
                                                value={this.state.nickname}
                                                onChange={this.onChangeHandler}
                                                onBlur={this.handleBlur('nickname')}
                                                aria-describedby="nicknameHelp"
                                                placeholder="Enter nickname"
                                            />
                                            {shouldMarkError('nickname') && <small id="nicknameHelp" className="form-text alert alert-danger"> {(!this.state.nickname ? 'nickname is required!' : 'nickname should be at least 4 and maximum 16 characters or number long!')}</small>}
                                        </div>

                                        <div className="form-group">
                                            <label htmlFor="birthday">Birthday</label>
                                            <DatePicker
                                                id="birthday"
                                                name="birthday"
                                                className={"form-control " + (shouldMarkError('birthday') ? "error" : "")}
                                                style={{width: 280, display: 'block', marginBottom: 10}}
                                                value={this.state.birthday}
                                                onChange={this.onDateChangeHandler}
                                                onBlur={this.handleBlur('birthday')}
                                                onSelect={this.onDateChangeHandler}
                                                // placement="topStart"
                                                format='MM-dd-y'
                                                placeholder='Select Date'
                                                aria-describedby="birthdayHelp"/>
                                            {shouldMarkError('birthday') && <small id="birthdayHelp" className="form-text alert alert-danger">birthday is required!</small>}
                                        </div>

                                        <div className="form-group">
                                            <label htmlFor="firstName" className="font-weight-bold" >First Name</label>
                                            <input
                                                type="text"
                                                className={"form-control " + (shouldMarkError('firstName') ? "error" : "")}
                                                id="firstName"
                                                name="firstName"
                                                value={this.state.firstName}
                                                onChange={this.onChangeHandler}
                                                onBlur={this.handleBlur('firstName')}
                                                aria-describedby="firstNameHelp"
                                                placeholder="Enter first name"
                                            />
                                            {shouldMarkError('firstName') && <small id="firstNameHelp" className="form-text alert alert-danger">{(!this.state.firstName ? 'First Name is required!' : 'First Name must start with a capital letter and contain only letters!')}</small>}
                                        </div>

                                        <div className="form-group">
                                            <label htmlFor="gender">Gender</label>
                                            <select id="gender"
                                                    name="gender"
                                                    className={"form-control " + (shouldMarkError('gender') ? "error" : "")}
                                                    defaultValue={this.state.gender}
                                                    onChange={this.onChangeHandler}
                                                    onSelect={this.onChangeHandler}
                                                    onBlur={this.handleBlur('gender')}
                                                    aria-describedby="genderHelp">
                                                <option hidden={true}>Default</option>
                                                <option value="male">Male</option>
                                                <option value="female">Female</option>

                                            </select>
                                            {shouldMarkError('gender') && <small id="genderHelp" className="form-text alert alert-danger">gender is required!</small>}
                                        </div>

                                        <div className="form-group">
                                            <label htmlFor="address" className="font-weight-bold" >Address</label>
                                            <input
                                                type="text"
                                                className={"form-control " + (shouldMarkError('address') ? "error" : "")}
                                                id="address"
                                                name="address"
                                                value={this.state.address}
                                                onChange={this.onChangeHandler}
                                                onBlur={this.handleBlur('address')}
                                                aria-describedby="addressHelp"
                                                placeholder="Enter address"
                                            />
                                            {shouldMarkError('address') && <small id="addressHelp" className="form-text alert alert-danger">{(!this.state.address ? 'Address is required!' : '')}</small>}
                                        </div>

                                        {showPicsButtons && <div className="form-group">
                                            <label htmlFor="picURl" className="font-weight-bold" >Profile image url</label>
                                            <input
                                                type="text"
                                                className={"form-control " + (shouldMarkError('picURl') ? "error" : "")}
                                                id="picURl"
                                                name="picURl"
                                                value={this.state.picURl}
                                                onChange={this.onChangeHandler}
                                                onBlur={this.handleBlur('picURl')}
                                                aria-describedby="picURlHelp"
                                                placeholder="Enter profile image url"
                                            />
                                            {shouldMarkError('picURl') && <small id="picURl" className="form-text alert alert-danger">{(!this.state.picURl ? 'Profile Image Url is required!' : '')}</small>}
                                        </div>}

                                    </section>

                                    <section className="right-section">
                                        { <div className="form-group">
                                <label htmlFor="email" className="font-weight-bold">Email Address</label>
                                <input
                                    type="email"
                                    className={"form-control " + (shouldMarkError('email') ? "error" : "")}
                                    id="email"
                                    name="email"
                                    value={this.state.email}
                                    onChange={this.onChangeHandler}
                                    onBlur={this.handleBlur('email')}
                                    aria-describedby="emailHelp"
                                    placeholder="Enter email"

                                />
                                {shouldMarkError('email') && <small id="emailHelp" className="form-text alert alert-danger">{(!this.state.email ? 'Email is required!' : 'Invalid e-mail address!')}</small>}
                            </div> }
                                        <div className="form-group">
                                            <label htmlFor="password" >Password</label>
                                            <Input
                                                type={this.state.touched.hiddenPass ? "text":"password"}
                                                className={"form-control " + (shouldMarkError('password') ? "error" : "")}
                                                id="password"
                                                name="password"
                                                value={this.state.password}
                                                onChange={this.onChangeHandler}
                                                onBlur={this.handleBlur('password')}
                                                aria-describedby="passwordHelp"
                                                placeholder="Enter password"
                                                endAdornment={
                                                    <InputAdornment position="end">
                                                        <IconButton
                                                            aria-label="toggle password visibility"
                                                            onClick={this.onShowPassword}>
                                                            {this.state.touched.hiddenPass ? <VisibilityOff />:<Visibility />}
                                                        </IconButton>
                                                    </InputAdornment>}
                                            />
                                            {shouldMarkError('password') && <small id="passwordHelp" className="form-text alert alert-danger">{(!this.state.password ? 'Password is required!' : 'Password should be at least 8 and maximum 30 characters long, and should contain number, upper case and lower case alphabet, special character!')}</small>}
                                        </div>

                                        <div className="form-group">
                                            <label htmlFor="lastName" className="font-weight-bold">Last Name</label>
                                            <input
                                                type="text"
                                                className={"form-control " + (shouldMarkError('lastName') ? "error" : "")}
                                                id="lastName"
                                                name="lastName"
                                                value={this.state.lastName}
                                                onChange={this.onChangeHandler}
                                                onBlur={this.handleBlur('lastName')}
                                                aria-describedby="lastNameHelp"
                                                placeholder="Enter last name"
                                            />
                                            {shouldMarkError('lastName') && <small id="lastNameHelp" className="form-text alert alert-danger">{(!this.state.lastName ? 'Last Name is required!' : 'Last Name must start with a capital letter and contain only letters!')}</small>}
                                        </div>

                                        <div className="form-group">
                                            <label htmlFor="city" className="font-weight-bold">City</label>
                                            <input
                                                type="text"
                                                className={"form-control " + (shouldMarkError('city') ? "error" : "")}
                                                id="city"
                                                name="city"
                                                value={this.state.city}
                                                onChange={this.onChangeHandler}
                                                onBlur={this.handleBlur('city')}
                                                aria-describedby="cityHelp"
                                                placeholder="Enter city"
                                            />
                                            {shouldMarkError('city') && <small id="cityHelp" className="form-text alert alert-danger">{(!this.state.city ? 'City is required!' : '')}</small>}
                                        </div>

                                        <div className="form-group">
                                            <label htmlFor="maritalStatus">Marital Status</label>
                                            <select id="maritalStatus"
                                                    name="maritalStatus"
                                                    className={"form-control " + (shouldMarkError('maritalStatus') ? "error" : "")}
                                                    defaultValue={this.state.maritalStatus}
                                                    onChange={this.onChangeHandler}
                                                    onBlur={this.handleBlur('maritalStatus')}
                                                    onSelect={this.onChangeHandler}
                                                    aria-describedby="maritalStatusHelp">
                                                <option hidden={true}>Default</option>
                                                <option value="single">Single</option>
                                                <option value="married">Married</option>

                                            </select>
                                            {shouldMarkError('maritalStatus') && <small id="maritalStatusHelp" className="form-text alert alert-danger">marital status is required!</small>}
                                        </div>

                                        {showPicsButtons && <div className="form-group">
                                            <label htmlFor="backgroundImageUrl" className="font-weight-bold" >Cover image url</label>
                                            <input
                                                type="text"
                                                className={"form-control " + (shouldMarkError('backgroundImageUrl') ? "error" : "")}
                                                id="backgroundImageUrl"
                                                name="backgroundImageUrl"
                                                value={this.state.backgroundImageUrl}
                                                onChange={this.onChangeHandler}
                                                onBlur={this.handleBlur('backgroundImageUrl')}
                                                aria-describedby="backgroundImageUrlHelp"
                                                placeholder="Enter cover image url"
                                            />
                                            {shouldMarkError('backgroundImageUrl') && <small id="backgroundImageUrlHelp" className="form-text alert alert-danger">{(!this.state.backgroundImageUrl ? 'Cover Image Url is required!' : '')}</small>}
                                        </div>}
                                    </section>
                                </div>

                                <div className="hr-styles"></div>
                                <div className="text-center">
                                    <button disabled={!isEnabled} type="submit" className="btn App-button-primary btn-lg m-3">Edit</button>
                                    <NavLink className="btn App-button-primary btn-lg m-3" to={`/home/profile/${this.props.id}`} role="button">Cancel</NavLink>
                                    {(isAdmin || isRoot) && <NavLink className="btn App-button-primary btn-lg m-3" to={`/home/users/all/${userService.getUserId()}`} role="button">All Users</NavLink>}
                                </div>
                            </form>
                        </div>
                    </section>
                </article>
            </Fragment>
        )
    }
}

const mapStateToProps = (state) => {
    return {
        timeLineUserData: state.timeLineUserData,
        loggedInUserData: state.loggedInUserData,
        updateUserData: state.updateUserData,
        changeTimeLineUserData: state.changeTimeLineUserData,
        changePicture: state.changePicture,
        changeAllFriends: state.changeAllFriends
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        updateUser: (loggedInUserId, userData) => { dispatch(updateUserAction(loggedInUserId, userData)) },
        changeTimeLineUser: (userId) => { dispatch(changeCurrentTimeLineUserAction(userId)) },
        changeAllFriends: (userId) => { dispatch(changeAllFriendsAction(userId)) },
        changeAllPictures: (userId) => { dispatch(changeAllPicturesAction(userId)) },
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(UserEditPage);

