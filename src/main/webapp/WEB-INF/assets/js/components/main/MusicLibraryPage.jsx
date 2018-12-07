import React, {Component} from "react"
import {connect} from "react-redux";
import PlaylistsList from "./PlaylistsList.jsx";
import {bindActionCreators} from "redux";
import {getPublicPlaylists, getFavouritePlaylists} from "../../store/actions/main.js";
import {typeSearch} from "../../store/actions/main";

class MusicLibraryPage extends Component {
    componentWillMount() {
        this.props.getPublicPlaylists();
        this.props.getFavouritePlaylists();
    }

    componentDidMount() {
        $(document).foundation();
    }

    render() {
        const {userPlaylists, publicPlaylists, favouritePlaylists, typeSearch} = this.props;
        return (
            <section>
                <div className="user-library">
                    <ul className="tabs" data-active-collapse="true" data-tabs id="collapsing-tabs">
                        <li className="tabs-title is-active"><a href="#privatePl" aria-selected="true">Private</a>
                        </li>
                        <li className="tabs-title"><a href="#publicPl">Public</a></li>
                        <li className="tabs-title"><a href="#favouritesPl">Playlists you liked</a></li>
                    </ul>
                    <div className="tabs-content artist-page" data-tabs-content="collapsing-tabs">
                        <div
                            className=" tabs-panel is-active grid-x grid-padding-x small-up-3 medium-up-5 large-up-6"
                            id="privatePl">
                            {userPlaylists.length > 0 ?
                                <PlaylistsList playlists={userPlaylists} play={typeSearch}/> :
                                <p className="empty-message">Nothing to show. Create you first private playlist and it
                                    will be here.</p>}
                        </div>
                        <div className="tabs-panel grid-x grid-padding-x small-up-3 medium-up-5 large-up-6"
                             id="publicPl">
                            {publicPlaylists.length > 0 ?
                                <PlaylistsList playlists={publicPlaylists} play={typeSearch} showlike/> :
                                <p className="empty-message">Nothing to show. Create you first public playlist and it
                                    will be here.</p>}
                        </div>
                        <div className=" tabs-panel grid-x grid-padding-x small-up-3 medium-up-5 large-up-6"
                             id="favouritesPl">
                            {favouritePlaylists.length > 0 ?
                                <PlaylistsList playlists={favouritePlaylists} play={typeSearch} showlike/> :
                                <p className="empty-message">Nothing to show. Click 'like' on any playlist and it will
                                    be here.</p>
                            }

                        </div>
                    </div>
                </div>
            </section>
        )
    }
}

const mapStateToProps = state => {
    return {
        userPlaylists: state.rootReducer.userPlaylists,
        publicPlaylists: state.rootReducer.publicPlaylists,
        favouritePlaylists: state.rootReducer.favouritePlaylists
    };
};

const mapDispatchToProps = dispatch => {
    return {
        getPublicPlaylists: bindActionCreators(getPublicPlaylists, dispatch),
        getFavouritePlaylists: bindActionCreators(getFavouritePlaylists, dispatch),
        typeSearch: (type, id, title) => dispatch(typeSearch(type, id, title)),
    };
};
export default connect(
    mapStateToProps,
    mapDispatchToProps
)(MusicLibraryPage);

