import React, { Fragment, Component } from 'react';
import { userService } from '../../infrastructure';
import TextareaAutosize from 'react-autosize-textarea';
import PictureSideBar from "./PictureSideBar";
import {CircleLoader} from "react-spinners";
import WritePicture from "../userPage/WritePicture";
import {toast} from "react-toastify";
import {ToastComponent} from "../common";
import '../userPage/css/UserGallery.css';
import {ButtonGroup} from "react-bootstrap";
import {css} from "@emotion/react";
const override = css`
        display: block;
        margin: 0 auto;
        border-color: red;
`;

export default class WritePost extends Component {
    constructor(props) {
        super(props)

        this.state = {
            content: '',
            imageUrl: [],
            createPostData: '',
            files: [],
            error: '',
            msg: '',
            ready: false,
            touched: {
                content: false,
            }
        };

        this.handleBlur = this.handleBlur.bind(this);
        this.onChangeHandler = this.onChangeHandler.bind(this);
        this.onSubmitHandler = this.onSubmitHandler.bind(this);
        this.uploadFile = this.uploadFile.bind(this);
        this.onFileChange = this.onFileChange.bind(this);
        this.removeCachePhoto = this.removeCachePhoto.bind(this);
        this.removeAll = this.removeAll.bind(this);
    }

    componentDidUpdate(prevProps, prevState, _preContext) {
        const loading = this.props.createPostData.loading || this.props.loadingAllPosts;

        if (!loading && this.state.createPostData !== this.props.createPostData) {
            this.setState({
                content: '',
                imageUrl: '',
                createPostData: this.props.createPostData,
            })
        }
    }

    changeUserData = (userdata) => {
        this.setState({loggedInUserProfilePicUrl: userdata.profilePicUrl})
    }

    onSubmitHandler(event) {
        event.preventDefault();
        if (!this.canBeSubmitted()) {
            return;
        }

        const {content, files} = this.state;
        /*const datas = this.uploadFile();*/

        this.props.createPost(content, files);
        this.removeAll();
    }

    onChangeHandler(event) {
        this.setState({
            [event.target.name]: event.target.value
        });
    }

    handleBlur = (field) => (event) => {
        this.setState({
            touched: { ...this.state.touched, [field]: true }
        });
    }

    canBeSubmitted() {
        const { content } = this.state;
        const errors = this.validate(content);
        const isDisabled = Object.keys(errors).some(x => errors[x])
        return !isDisabled;
    }

    validate = (content) => {
        return {
            content: content.length === 0,
        }
    }

    uploadFile = () => {
        this.setState({ error: '', msg: '' });

        for(let i=0; i<this.state.files.length; i++){
            if (!this.state.files[i]) {
                toast.error(<ToastComponent.errorToast text='Please upload a file.' />, {
                    position: toast.POSITION.TOP_RIGHT
                });
                return;
            }
            if (this.state.files[i].size >= 2000000) {
                toast.error(<ToastComponent.errorToast text='File size exceeds limit of 2MB.' />, {
                    position: toast.POSITION.TOP_RIGHT
                });
                return;
            }
        }

        const userId = this.props.loggedInUser.id;
        let datas = [];
        for(let i=0; i<this.state.files.length; i++){
            let data = new FormData();
            data.append('file', this.state.files[i]);
            data.append('loggedInUserId', userId);
            datas.push(data)
        }
        return datas;
    }


    removeCachePhoto = (index) => {
        let change = this.state.files;
        change.splice(index,1);
        this.setState({
            files:change
        })
    }

    removeAll = () => {
        this.setState({
            files:[]
        })
    }



    onFileChange = (event) => {
        event.preventDefault();
        if(event.target.files[0]) {
            let change = this.state.files
            change.push(event.target.files[0])
            /*console.log(event.target.files + "============================")*/
            this.setState({
                files: change,
                ready: true
            })/*, () => this.uploadFile());*/
        }
    }

    render() {
        const { content } = this.state;
        const errors = this.validate(content);
        const isEnabled = !Object.keys(errors).some(x => errors[x]);
        const displayButon = isEnabled ? '' : 'hidden';

        const imageClass = userService.getImageSize(this.props.loggedInUser.profilePicURL);
        const loggedInUserProfilePicUrl = this.props.loggedInUser.profilePicURL;
        const loggedInUserFirstName = this.props.loggedInUser.firstName;

        let formattedUsername = userService.formatUsername(loggedInUserFirstName)

        return (
            <Fragment>
                <section className="posts-section">
                    <div className="write-post" id="create-post-button-container">
                        <div className="post">
                            <div className="post-image">
                                <img className={imageClass} src={loggedInUserProfilePicUrl} alt="" />
                            </div>
                            <div className="post-area-container">
                                <form id="post-form" onSubmit={this.onSubmitHandler}>
                                    <div className="" id="post-textarea-form-group">
                                        <TextareaAutosize
                                            name="content"
                                            id="content"
                                            className="post-textarea"
                                            value={this.state.content}
                                            onChange={this.onChangeHandler}
                                            onBlur={this.handleBlur('content')}
                                            aria-describedby="contentHelp"
                                            placeholder={`What's on your mind, ${formattedUsername}?`}
                                        >
                                        </TextareaAutosize>

                                        {this.state.files.length > 0
                                        &&
                                        <Fragment>
                                            <div className="hr-styles" style={{ 'width': '90%' }}></div>
                                            <ul className="grid-container">
                                                {this.state.files.map((picture,index) => <WritePicture id={index} removeCachePhoto={this.removeCachePhoto}  picUrl={URL.createObjectURL(picture)}/>)}
                                            </ul>
                                        </Fragment>}
                                    </div>

                                    <div className="text-right">
                                        <ButtonGroup vertical>
                                            <button disabled={!isEnabled} style={{ 'visibility': `${displayButon}` }} type="submit" className="btn uiButtonGroup post-button-fbPhotoCurationControl App-button-primary ">POST</button>
                                            <div></div>
                                            <button className="btn uiButtonGroup post-button-fbPhotoCurationControl App-button-primary" disabled={!isEnabled} style={{ 'visibility': `${displayButon}` }} type="button">
                                                <label className="cursor-pointer" id="upload" htmlFor="fileUpload" > ADD PHOTO
                                                    <input className="cursor-pointer" id="fileUpload" onChange={this.onFileChange} type="file" />
                                                </label>
                                            </button>
                                        </ButtonGroup>

                                    </div>



                                </form>
                            </div>
                        </div>
                    </div>
                </section>
            </Fragment>
        )
    }
}
