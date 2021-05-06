import React, { Fragment } from 'react';
import { userService } from '../../infrastructure';
import Comment from './Comment';
import {CircleLoader} from "react-spinners";
import Picture from "../userPage/Picture";
import {css} from "@emotion/react";
const override = css`
        display: block;
        margin: 0 auto;
        border-color: red;
`;

const Post = (props) => {
    const imageClass = userService.getImageSize(props.imageUrl);
    const imageClassUserPick = userService.getImageSize(props.loggedInUserProfilePicUrl);

    let isRoot = userService.isRoot();
    let isPostCreator = (props.authorId === props.currentLoggedInUserId);
    let isTimeLineUser = (props.timelineUserId === props.currentLoggedInUserId);

    const dayTime = props.time.hour <= 12 ? 'AM' : 'PM';
    const month = props.time.month.substring(0, 1) + props.time.month.substring(1, 5).toLowerCase()
    const hour = props.time.hour < 10 ? '0' + props.time.hour : props.time.hour;
    const minute = props.time.minute < 10 ? '0' + props.time.minute : props.time.minute;
    /*const commentList = props.getComment(props.id);
    console.log(props.id);
    console.log("test======================="+ commentList);*/

    /*const formattedName = userService.formatUsername(props.loggedInUserFirstName, props.loggedInUserLastName);*/

    return (
        <Fragment>
            <div className="post-wrapper" id="container">
                <div className="post-content-article-header ">
                    <div className="post-content-article-image">
                        <img className={imageClassUserPick} src={props.loggedInUserProfilePicUrl} alt="bender" />
                    </div>
                    <div className="post-content-article-description">
                        <p className="post-user-info">{props.author} </p>
                        <p className="post-description"> {props.time.dayOfMonth} {month} {hour}:{minute} {dayTime}</p>
                    </div>
                </div>
                <div className="post-content">
                    <p className="">{props.content} </p>
                </div>

                {props.imageUrls.length > 0  && <div className="post-media">
                    <Fragment>
                        <div className="hr-styles" style={{ 'width': '90%' }}></div>
                        <ul className="grid-container">
                            {props.imageUrls.map((url) =>
                                <li>
                                    <div id="container">
                                        <article className="card " id="contauner">
                                            <div className="media">
                                                <img className={userService.getImageSize(url)} src={url} alt="pic1" />
                                            </div>
                                        </article>
                                    </div>
                                </li>)}
                        </ul>
                    </Fragment>
                </div>}

                <div className="post-footer">
                    <div className="post-left-side-icons-container">
                        <ul>
                            <li className="like-icon">
                                <div className="like-button" onClick={props.addLike.bind(this, props.id)}> <i className="fas fa-thumbs-up"></i></div>
                            </li>
                            <li className="like-count">
                                <div >{props.likeNum.length}</div>
                            </li>
                            <li>
                                <i className="fas fa-share"></i>
                            </li>
                        </ul>
                    </div>

                    <div className="post-right-side-icons-container">
                        <div className="comment-icon">
                            <i className="fas fa-comments"></i>
                        </div>
                        <p>{props.commentViewList ? props.commentViewList.length : 0}</p>
                    </div>
                </div>

                {(isRoot || isPostCreator || isTimeLineUser) && <div onClick={props.removePost.bind(this, props.id)}>
                    <div className="btn uiButtonGroup fbPhotoCurationControl  delete-button" ><i className="far fa-trash-alt "></i></div>
                </div>}
            </div>

            <div className="comment-wrapper" id="comment-container">
                {props.commentViewList && props.commentViewList.length > 0 && props.commentViewList.map((comment) =>
                    <Comment
                        key={comment.commentId}
                        addLikeComment={props.addLikeComment}
                        removeComment={props.removeComment}
                        timelineUserId={props.timelineUserId}
                        currentLoggedInUserId={props.currentLoggedInUserId}
                        {...comment}
                    />)}
            </div>
        </Fragment>
    )
}

export default Post