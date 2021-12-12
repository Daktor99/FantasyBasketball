import {Component} from "react";
import {Table} from "semantic-ui-react";

class PlayerTable extends Component {
    render() {
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

                    <Table.Body>

                    </Table.Body>

                    <Table.Footer fullWidth>
                        <Table.Row>
                            <Table.HeaderCell/>
                            <Table.HeaderCell colSpan='11'>

                            </Table.HeaderCell>
                        </Table.Row>
                    </Table.Footer>

                </Table>
            </div>
        );
    }
}