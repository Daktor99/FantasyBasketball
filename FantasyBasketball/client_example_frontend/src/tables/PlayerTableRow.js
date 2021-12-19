import {Component} from "react";
import {TableCell, TableRow} from "semantic-ui-react";
import {CLIENT_GOOGLE_ID} from "../Constants";

class PlayerTableRow extends Component {
    constructor(props) {
        super(props);
        this.state = {
            player_data: null
        }
    }

    async componentWillMount() {

        const player_id = this.props.player.player_id
        const team_id = this.props.team_id
        const input = `/fantasyStats?player_id=${encodeURIComponent(player_id)}&team_id=${encodeURIComponent(team_id)}`

        await fetch(input, {
            headers: {
                Accept: 'application/json',
                'token': CLIENT_GOOGLE_ID
            }
        })
            .then(resp => resp.json())
            .then(data => {
                this.setState({
                    player_data: data[0]
                })
                console.log("Success fetching player data for player_id " + player_id)
                // Get Players

            })
            .catch(error => {
                console.log("Error fetching player data for player_id " + player_id + ": " + error)
                window.alert("Error fetching player data, reload page")
            })

    }

    render() {
        const {player} = this.props
        const {player_data} = this.state
        return (
            <TableRow>
                <TableCell>
                    {player.first_name + " " + player.last_name}
                </TableCell>
                <TableCell>
                    {player.position}
                </TableCell>
                <TableCell>
                    {player_data ? player_data.two_points : 0}
                </TableCell>
                <TableCell>
                    {player_data ? player_data.three_points : 0}
                </TableCell>
                <TableCell>
                    {player_data ? player_data.free_throws : 0}
                </TableCell>
                <TableCell>
                    {player_data ? player_data.rebounds : 0}
                </TableCell>
                <TableCell>
                    {player_data ? player_data.assists : 0}
                </TableCell>
                <TableCell>
                    {player_data ? player_data.blocks : 0}
                </TableCell>
                <TableCell>
                    {player_data ? player_data.steals : 0}
                </TableCell>
                <TableCell>
                    {player_data ? player_data.turnovers : 0}
                </TableCell>
                <TableCell>
                    {player_data ? player_data.tot_points : 0}
                </TableCell>
            </TableRow>
        );
    }
}

export default PlayerTableRow