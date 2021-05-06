import React from 'react';
import { userService } from '../../infrastructure'

const WritePicture = (props) => {
    const imageClass = userService.getImageSize(props.imageUrl);

    return (
        <li>
            <div id="container">
                <article className="card " id="contauner">
                    <div className="media">
                        <img className={imageClass} src={props.picUrl} alt="pic1" />
                    </div>
                    <div  onClick={props.removeCachePhoto.bind(this, props.id)}>
                        <div className="btn fbPhotoCurationControl inner uiButtonGroup delete-button" ><i className="far fa-trash-alt "></i></div>
                    </div>
                </article>
            </div>
        </li>
    )
}

export default WritePicture;