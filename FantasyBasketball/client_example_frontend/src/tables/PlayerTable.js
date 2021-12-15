import {Component} from "react";
import {Button, Icon, Table, TableBody} from "semantic-ui-react";
import PlayerTableRow from "./PlayerTableRow";
import {MAX_TEAM_SIZE} from "../Constants";

class PlayerTable extends Component {


    render() {
        const {players} = this.props
        const {team_id} = this.props
        return (
            <div>
                <Table celled>
                    <Table.Header>
                        <Table.Row>
                            <Table.HeaderCell>Player Name</Table.HeaderCell>
                            <Table.HeaderCell>Position</Table.HeaderCell>
                            <Table.HeaderCell>Two Points</Table.HeaderCell>
                            <Table.HeaderCell>Three Points</Table.HeaderCell>
                            <Table.HeaderCell>Free Throws</Table.HeaderCell>
                            <Table.HeaderCell>Rebounds</Table.HeaderCell>
                            <Table.HeaderCell>Assists</Table.HeaderCell>
                            <Table.HeaderCell>Blocks</Table.HeaderCell>
                            <Table.HeaderCell>Steals</Table.HeaderCell>
                            <Table.HeaderCell>Turnovers</Table.HeaderCell>
                            <Table.HeaderCell>Total Points</Table.HeaderCell>
                        </Table.Row>
                    </Table.Header>

                    <TableBody>
                        {players.map(player =>
                            <PlayerTableRow player={player} team_id={team_id} removePlayer={this.props.removePlayer}/>
                        )}
                    </TableBody>

                    <Table.Footer fullWidth>
                        <Table.Row>
                            <Table.HeaderCell colSpan='12'>
                                <Button
                                    disabled={players.length >= MAX_TEAM_SIZE || !this.props.isOwner}
                                    floated='right'
                                    icon
                                    labelPosition='left'
                                    primary
                                    size='small'
                                >
                                    <Icon
                                        name='user'/> {players.length >= MAX_TEAM_SIZE ? 'Your team is full' : 'Add a Player'}
                                </Button>
                            </Table.HeaderCell>
                        </Table.Row>
                    </Table.Footer>

                </Table>
            </div>
        );
    }
}

export default PlayerTable