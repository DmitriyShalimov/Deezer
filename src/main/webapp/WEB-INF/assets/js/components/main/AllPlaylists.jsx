import React, {Component} from "react"
import "./css/music-library.scss";
import {connect} from "react-redux";
import PlaylistsList from "./PlaylistsList.jsx";
import {bindActionCreators} from "redux";
import {getAllPublicPlaylists, typeSearch} from "../../store/actions/main.js";
import {withRouter} from "react-router-dom";

class AllPlaylists extends Component {
    componentWillMount() {
        this.props.getAllPublicPlaylists();
    }

    render() {
        const {allPublicPlaylists, typeSearch, likePlaylist} = this.props;
        console.log(allPublicPlaylists);
        return (
            <section>
                <div className="user-library">
                    <ul className="tabs" data-active-collapse="true" data-tabs id="collapsing-tabs">
                        <li className="tabs-title is-active"><a href="#allPl" aria-selected="true">All Playlists</a>
                        </li>
                    </ul>
                    <div className="tabs-content artist-page" data-tabs-content="collapsing-tabs">
                        <div className="tabs-panel is-active grid-x grid-padding-x small-up-3 medium-up-4 large-up-6" id="allPl">
                            {allPublicPlaylists.length > 0 ?
                                <PlaylistsList playlists={allPublicPlaylists} play={typeSearch} like={likePlaylist}
                                               showlike/> :
                                <p className="empty-message">Nothing to show. Nobody created a playlist.</p>}
                        </div>
                    </div>
                </div>
            </section>
        )
    }
}

const mapStateToProps = state => {
    return {
        allPublicPlaylists: state.rootReducer.allPublicPlaylists,
    };
};

const mapDispatchToProps = dispatch => {
    return {
        getAllPublicPlaylists: bindActionCreators(getAllPublicPlaylists, dispatch),
        typeSearch: (type, id, title) => dispatch(typeSearch(type, id, title)),
    };
};
export default withRouter(connect(
    mapStateToProps,
    mapDispatchToProps
)(AllPlaylists));

