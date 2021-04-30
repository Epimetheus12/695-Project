// import logo from './logo.svg';
import './App.css';

import React, { Component, Fragment, lazy, Suspense } from 'react';
import { Route, Switch, withRouter } from 'react-router-dom';

import { Footer } from './components/common';
import Navbar from './components/homePage/NavBar';
import { ToastComponent } from './components/common'
import { userService } from './infrastructure';
import { ToastContainer, toast, Zoom } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.min.css'
import './static/css/App.css';
import { css } from '@emotion/react';
import { CircleLoader } from 'react-spinners';

import { connect } from 'react-redux';
/*const override = require("./config-override");*/
const logoutAction = require('./store/actions/authActions').logoutAction();

const StartPage = lazy(() => import('./components/authPage/StartPage'))
const RegisterPage = lazy(() => import('./components/authPage/RegisterPage'))
const LoginPage = lazy(() => import('./components/authPage/LoginPage'))
const HomePage = lazy(() => import('./components/homePage/HomePage'))
const ErrorPage = lazy(() => import('./components/common/ErrorPage'))

const override = css`
        display: block;
        margin: 8rem auto;
        border-color: red;
`;

class App extends Component {
  constructor(props) {
    super(props)

    this.onLogout = this.onLogout.bind(this);
  }

  onLogout() {
    this.props.logout();

    toast.success(<ToastComponent.successToast text={`You have been successfully logged out.`} />, {
      position: toast.POSITION.TOP_RIGHT
    });

    this.props.history.push('/login');
  }

  render() {
    const loggedIn = userService.isTheUserLoggedIn();

    return (
        <Fragment>
          <Navbar loggedIn={localStorage.getItem('token') != null} onLogout={this.onLogout} {...this.props} />
          <ToastContainer transition={Zoom} closeButton={false} />
          <Suspense fallback={
            <div className='sweet-loading'>
              <CircleLoader
                  css={override}
                  sizeUnit={"px"}
                  size={150}
                  color={'#61dafb'}
                  loading={true}
              />
            </div>}>
            <Switch>
              <Route exact path="/" component={StartPage} />
              {!loggedIn && <Route exact path="/register" component={RegisterPage} />}
              {!loggedIn && <Route exact path="/login" component={LoginPage} />}
              {loggedIn && <Route path="/home" component={HomePage} />}
              <Route exact path="/error" component={ErrorPage} />
              <Route component={ErrorPage} />
            </Switch>
          </Suspense>
          <Footer />
        </Fragment>
    )
  }
}

function mapDispatchToProps(dispatch) {
  return {
    logout: () => dispatch(logoutAction),
  }
}

export default withRouter(connect(null, mapDispatchToProps)(App));


// function App() {
//   return (
//     <div className="App">
//       <header className="App-header">
//         <img src={logo} className="App-logo" alt="logo" />
//         <p>
//           Edit <code>src/App.js</code> and save to reload.
//         </p>
//         <a
//           className="App-link"
//           href="https://reactjs.org"
//           target="_blank"
//           rel="noopener noreferrer"
//         >
//           Learn React
//         </a>
//       </header>
//     </div>
//   );
// }
//
// export default App;
