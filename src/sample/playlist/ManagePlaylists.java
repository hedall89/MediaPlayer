package sample.playlist;

/**
 * Class to manage existing playlists in the Database.
 *
 *
 */
public class ManagePlaylists
{
    public String get_PLAYLISTID() {
        return _PLAYLISTID;
    }

    public void set_PLAYLISTID(String _PLAYLISTID) {
        this._PLAYLISTID = _PLAYLISTID;
    }

    private String _PLAYLISTVID = null;
    private String _PLAYLIST;
    private String _PLAYLISTID;
    public ManagePlaylists(String playlistvid, String playlist) {
        _PLAYLISTVID = playlistvid;
        _PLAYLIST = playlist;
    }

    public ManagePlaylists() {

    }

    public String get_PLAYLISTVID() {
        return _PLAYLISTVID;
    }

    public void set_PLAYLISTVID(String _PLAYLISTVID) {
        this._PLAYLISTVID = _PLAYLISTVID;
    }

    public String get_PLAYLIST() {
        return _PLAYLIST;
    }

    public void set_PLAYLIST(String _PLAYLIST) {
        this._PLAYLIST = _PLAYLIST;
    }
}
