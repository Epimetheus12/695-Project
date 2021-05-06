import React, { Component, Fragment } from 'react';
import { NavLink } from 'react-router-dom';
import { toast } from 'react-toastify';
import { ToastComponent } from '../common';
import Friend from './Friend';
import './css/UserFriends.css';

import { connect } from 'react-redux';
import {
    changeCurrentTimeLineUserAction,
    changeAllFriendsAction,
    removeFriendAction,
    changeAllFollowerAction, addFriendAction
} from '../../store/actions/userActions';
import { changeAllPicturesAction } from '../../store/actions/pictureActions';
/*import {pickerDefaultProps} from "rsuite/src/Picker/propTypes";*/

class UserFollowerPage extends Component {
    constructor(props) {
        super(props)

        this.state = {
            friendsArr: this.props.friendsArr,
            followerArr: this.props.followerArr,
            id: this.props.id,
        };
        this.isInFollowed = this.isInFollowed.bind(this);
        this.addFriend = this.addFriend.bind(this);
    }

    componentDidMount() {
        const currentTimeLineUserId = this.props.match.params.id;
        if (currentTimeLineUserId !== this.props.timeLineUserData.id) {
            this.props.changeTimeLineUser(currentTimeLineUserId);
            this.props.changeAllPictures(currentTimeLineUserId);
            this.props.changeAllFriends(currentTimeLineUserId);

        }
        this.props.changeAllFollower(currentTimeLineUserId);

    }

    componentDidUpdate(prevProps, prevState, _prevContext) {
        const errorMessage = this.getErrorMessage(prevProps);
        const successMessage = this.getSuccessMessage(prevProps)

        if (errorMessage) {
            toast.error(<ToastComponent.errorToast text={errorMessage} />, {
                position: toast.POSITION.TOP_RIGHT
            });
        } else if (successMessage) {
            toast.success(<ToastComponent.successToast text={successMessage} />, {
                position: toast.POSITION.TOP_RIGHT
            });
        }
    }

    getSuccessMessage(prevProps) {
        if (!this.props.fetchAllFollower.hasError && this.props.fetchAllFollower.message && this.props.fetchAllFollower !== prevProps.fetchAllFollower) {
            return this.props.fetchAllFollower.message;
        }
        else if (!this.props.removeFriend.hasError && this.props.removeFriend.message && this.props.removeFriend !== prevProps.removeFriend) {
            return this.props.removeFriend.message;
        }
        return null;
    }

    getErrorMessage(prevProps) {
        if (this.props.fetchAllFollower.hasError && prevProps.fetchAllFollower.error !== this.props.fetchAllFollower.error) {
            return this.props.fetchAllFollower.message || 'Server Error';
        }
        else if (this.props.removeFriend.hasError && prevProps.removeFriend.error !== this.props.removeFriend.error) {
            return this.props.removeFriend.message || 'Server Error';
        }
        return null;
    }

    removeFriend = (friendToRemoveId, event) => {
        const loggedInUserId = this.props.loggedInUserData.id
        this.props.deleteFriend(loggedInUserId, friendToRemoveId);
    }

    isInFollowed = (id) =>{
        let flag = false;
        if(this.props.friendsArr){
            for(let i = 0; i < this.props.friendsArr.length; i++){
                if(this.props.friendsArr[i].id === id){
                    flag =true;
                    return flag;
                }
            }
        }
        /*console.log("Test======================="+this.p rops.followerArr[0])*/

    }

    addFriend = (friendCandidateId) => {
        const loggedInUserId = this.props.loggedInUserData.id;
        this.props.addFriend(loggedInUserId, friendCandidateId);
    }

    render() {
        const isTheCurrentLoggedInUser = (this.props.loggedInUserData.id === this.props.timeLineUserData.id);


        return (
            <Fragment >
                <article className="main-article-shared-content">
                    <section className="friend-content-section">
                        <div className="container col-md-12 text-center mb-5">
                            <h1 className="text-center font-weight-bold mt-4" style={{ 'margin': '1rem auto' }}>Followers</h1>
                            <div className="hr-styles"></div>
                            <section className="friend-section" >
                                {this.props.followerArr.length > 0 ?
                                    this.props.followerArr.map((friend) =>
                                        (this.isInFollowed(friend.id) && isTheCurrentLoggedInUser) ?
                                            <Friend
                                                key={friend.id}
                                                {...friend}
                                                firstButtonLink={`/home/profile/${friend.id}`}
                                                secondButtonLink={`/`}
                                                firstButtonText={'VIEW PROFILE'}
                                                secondButtonText={'UNFOLLOW'}
                                                secondButtonOnClick={this.removeFriend}
                                            />
                                            :
                                            isTheCurrentLoggedInUser ?
                                            <Friend
                                                key={friend.id}
                                                {...friend}
                                                firstButtonLink={`/home/profile/${friend.id}`}
                                                secondButtonLink={`/`}
                                                firstButtonText={'VIEW PROFILE'}
                                                secondButtonText={'FOLLOW'}
                                                secondButtonOnClick={this.addFriend}
                                            /> :
                                            <Friend
                                                key={friend.id}
                                                {...friend}
                                                firstButtonLink={`/home/profile/${friend.id}`}
                                                secondButtonLink={`/home/comments/${this.props.loggedInUserData.id}`}
                                                firstButtonText={'VIEW PROFILE'}
                                                secondButtonText={'HOME'}
                                            />)
                                    :
                                    (<Fragment>
                                            <h2>You don't have any followers.</h2>

                                            <div className="hr-styles"></div>
                                        </Fragment>
                                    )
                                }
                            </section>
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

        /*fetchAllLogs: state.fetchAllLogs,
        logsArr: state.fetchAllLogs.logsArr,*/

        friendsArr: state.fetchAllFriends.friendsArr,
        fetchAllFriends: state.fetchAllFriends,

        followerArr: state.fetchAllFollower.followerArr,
        fetchAllFollower: state.fetchAllFollower,
        removeFriend: state.removeFriend
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        changeTimeLineUser: (userId) => { dispatch(changeCurrentTimeLineUserAction(userId)) },
        changeAllFriends: (userId) => { dispatch(changeAllFriendsAction(userId)) },
        changeAllFollower: (userId) => {dispatch(changeAllFollowerAction(userId))},
        changeAllPictures: (userId) => { dispatch(changeAllPicturesAction(userId)) },
        addFriend: (loggedInUserId, friendCandidateId) => { dispatch(addFriendAction(loggedInUserId, friendCandidateId)) },
        deleteFriend: (loggedInUserId, friendToRemoveId) => { dispatch(removeFriendAction(loggedInUserId, friendToRemoveId)) }
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(UserFollowerPage);