import React, { Component, Fragment } from 'react';
// import '../../styles/FormPages.css'
import { toast } from 'react-toastify';
import { ToastComponent } from '../common';
// import placeholder_user_image from '../../assets/images/placeholder.png';
// import default_background_image from '../../assets/images/default-background-image.jpg';
import { connect } from 'react-redux';
import { registerAction, redirectAction } from '../../store/actions/authActions'
import { makeStyles } from '@material-ui/core/styles';
import DatePicker from "react-date-picker";
import "react-date-picker/dist/DatePicker.css";
import "react-calendar/dist/Calendar.css";
import {IconButton, InputAdornment} from "@material-ui/core";
import Input from '@material-ui/core/Input';
import Visibility from '@material-ui/icons/Visibility';
import VisibilityOff from '@material-ui/icons/VisibilityOff';

class RegisterPage extends Component {
    constructor(props) {
        super(props)

        this.state = {
            username: '',
            nickname: '',
            gender: '',
            maritalStatus: '',
            birthday: '',
            email: '',
            password: '',
            confirmPassword: '',
            firstName: '',
            lastName: '',
            address: '',
            city: '',
            picURl: '',
            touched: {
                username: false,
                nickname: false,
                gender: false,
                maritalStatus: false,
                birthday: false,
                email: false,
                password: false,
                confirmPassword: false,
                firstName: false,
                lastName: false,
                address: false,
                city: false,
                hiddenPass: false
            }
        };

        this.onChangeHandler = this.onChangeHandler.bind(this);
        this.onSubmitHandler = this.onSubmitHandler.bind(this);
        this.onDateChangeHandler = this.onDateChangeHandler.bind(this);
        this.onShowPassword = this.onShowPassword.bind(this);
    }

    componentDidUpdate(prevProps, prevState, _prevContext){
        if (this.props.registerError.hasError && prevProps.registerError !== this.props.registerError) {
            toast.error(<ToastComponent.errorToast text={this.props.registerError.message} />, {
                position: toast.POSITION.TOP_RIGHT
            });
        } else if (this.props.registerSuccess) {
            this.props.redirect();

            toast.success(<ToastComponent.successToast text={this.props.registerMessage} />, {
                position: toast.POSITION.TOP_RIGHT
            });

            this.props.history.push('/login');
        }
    }

    onChangeHandler(event) {
        console.log(event);
        this.setState({
            [event.target.name]: event.target.value
        });
    }

    onDateChangeHandler(date) {
        console.log("===========test=========" + new Date(date).toUTCString())
        const dateString = new Date(date).toUTCString()
        this.setState({
            ['birthday']: date
        });
    }

    onShowPassword() {
        this.setState({"touched":{hiddenPass: !this.state.touched.hiddenPass}})
    }

    onSubmitHandler(event) {
        event.preventDefault();

        if (!this.canBeSubmitted()) {
            return;
        }

        const { touched, ...otherProps } = this.state;
        this.props.register(otherProps)
    }

    canBeSubmitted() {
        const { username, nickname, gender, maritalStatus, birthday, email, firstName, lastName, password, confirmPassword, address, city } = this.state;
        const errors = this.validate(username, nickname, gender, maritalStatus, birthday, email, firstName, lastName, password, confirmPassword, address, city);
        const isDisabled = Object.keys(errors).some(x => errors[x])
        return !isDisabled;
    }

    handleBlur = (field) => (event) => {
        this.setState({
            touched: { ...this.state.touched, [field]: true }
        });

    }

    validate = (username, nickname, gender, maritalStatus, birthday, email ,firstName, lastName, password, confirmPassword, address, city) => {
        const emailRegex = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$/;
        const nameRegex = /^[A-Z]([a-zA-Z]+)?$/;
        const nickNameRegex =/^([0-9a-zA-Z]+)?$/
        const passwordRegex = /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[.,!@#$%^&+=])(?=\S+$).{8,30}$/
        const testUsername = nickNameRegex.test(username)
        const testNickname = nickNameRegex.test(nickname)
        const testPassword = passwordRegex.test(password)
        const testEmail = emailRegex.test(email)
        const testFirstName = nameRegex.test(firstName)
        const testLastName = nameRegex.test(lastName)
        const testBirthday = isNaN(new Date(birthday).getFullYear())
        return {
            username: username.length < 4 || username.length > 16 || !testUsername,
            nickname: nickname.length < 4 || nickname.length > 16 || !testNickname,
            gender: gender.length === 0,
            maritalStatus: maritalStatus.length === 0,
            birthday: testBirthday,
            email: email.length === 0 || !testEmail,
            firstName: firstName.length === 0 || !testFirstName,
            lastName: lastName.length === 0 || !testLastName,
            password: password.length < 8 || password.length > 30 || !testPassword,
            confirmPassword: confirmPassword.length === 0 || confirmPassword !== password,
            address: address.length === 0,
            city: city.length === 0,
        }
    }

    render() {
        const { username, nickname, gender, maritalStatus, birthday, email, firstName, lastName, password, confirmPassword, address, city } = this.state;
        const errors = this.validate(username, nickname, gender, maritalStatus, birthday, email, firstName, lastName, password, confirmPassword, address, city);
        const isEnabled = !Object.keys(errors).some(x => errors[x])

        const shouldMarkError = (field) => {
            const hasError = errors[field];
            const shouldShow = this.state.touched[field];
            return hasError ? shouldShow : false;
        }

        return (
            <Fragment>
                <section className="pt-3">
                    <div className="container register-form-content-section pb-4 ">
                        <h1 className="text-center font-weight-bold mt-4" style={{ 'margin': '1rem auto', 'paddingTop': '2rem' }}>Register</h1>
                        <div className="hr-styles" style={{ 'width': '70%' }}></div>

                        <form className="Register-form-container" onSubmit={this.onSubmitHandler}>

                            <div className="section-container">
                                <section className="register-section">
                                    <div className="form-group">
                                        <label htmlFor="username" >Username</label>
                                        <input
                                            type="text"
                                            className={"form-control " + (shouldMarkError('username') ? "error" : "")}
                                            id="username"
                                            name="username"
                                            value={this.state.username}
                                            onChange={this.onChangeHandler}
                                            onBlur={this.handleBlur('username')}
                                            aria-describedby="usernameHelp"
                                            placeholder="Enter username"
                                        />
                                        {shouldMarkError('username') && <small id="usernameHelp" className="form-text alert alert-danger"> {(!this.state.username ? 'Username is required!' : 'Username should be at least 4 and maximum 16 characters or number long!')}</small>}
                                    </div>

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
                                        <label htmlFor="gender">Gender</label>
                                        <select id="gender"
                                                name="gender"
                                                className={"form-control " + (shouldMarkError('gender') ? "error" : "")}
                                                defaultValue={this.state.gender}
                                                de
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
                                        <label htmlFor="firstName" >First Name</label>
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
                                        <label htmlFor="address" >Address</label>
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
                                        <label htmlFor="email" >Email Address</label>
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
                                    </div>

                                    <div className="form-group">
                                        <label htmlFor="lastName" >Last Name</label>
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
                                        <label htmlFor="city" >City</label>
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
                                        <label htmlFor="confirmPassword" >Confirm Password</label>
                                        <input
                                            type="password"
                                            className={"form-control " + (shouldMarkError('confirmPassword') ? "error" : "")}
                                            id="confirmPassword"
                                            name="confirmPassword"
                                            value={this.state.confirmPassword}
                                            onChange={this.onChangeHandler}
                                            onBlur={this.handleBlur('confirmPassword')}
                                            aria-describedby="confirmPasswordHelp"
                                            placeholder="Confirm your password"
                                        />
                                        {shouldMarkError('confirmPassword') && <small id="confirmPasswordHelp" className="form-text alert alert-danger">Passwords do not match!</small>}
                                    </div>
                                </section>
                            </div>

                            <div className="text-center">
                                <button disabled={!isEnabled} type="submit" className="btn App-button-primary btn-lg m-3">Register</button>
                            </div>
                        </form>
                    </div>

                </section>
            </Fragment>
        )
    }
};

function mapStateToProps(state) {
    return {
        registerSuccess: state.register.success,
        registerMessage: state.register.message,
        registerError: state.registerError
    }
}

function mapDispatchToProps(dispatch) {
    return {
        register: (userData) =>
            dispatch(registerAction(userData)),
        redirect: () => dispatch(redirectAction())
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(RegisterPage);