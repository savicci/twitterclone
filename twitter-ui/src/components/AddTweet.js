import React, {Component} from "react";
import {Alert, Button, Form, FormGroup, FormLabel} from "react-bootstrap";
import {addTweet} from "../utils/APIUtils";

export default class AddTweet extends Component {
    constructor(props) {
        super(props);

        this.state = {
            tweetText: '',
            showDanger: false,
            showSuccess: false
        }
    }

    render() {
        const tweetRef = React.createRef();
        return (
            <div>
                <Alert style={{marginTop: "16px"}} variant="danger" show={this.state.showDanger} dismissible
                       onClose={() => this.setState((prevState) => {
                           return {
                               ...prevState,
                               showDanger: false,
                           }
                       })}>
                    <Alert.Heading>Could not add tweet</Alert.Heading>
                </Alert>
                <Alert style={{marginTop: "16px"}} variant="success" show={this.state.showSuccess} dismissible
                       onClose={() => this.setState((prevState) => {
                           return {
                               ...prevState,
                               showSuccess: false,
                           }
                       })}>
                    <Alert.Heading>Added tweet succesfully</Alert.Heading>
                </Alert>
                <Form onSubmit={(event) => {
                    event.preventDefault();
                    const request = Object.assign({}, {userName: this.props.user.username, text: this.state.tweetText});
                    console.log(JSON.stringify(request));
                    addTweet(request)
                        .then(response => {
                            console.log(response);
                            this.setState({showDanger: false, showSuccess: true, tweetText: ''});
                        })
                        .catch(error => {
                            this.setState((prevState) => {
                                return {
                                    ...prevState,
                                    showDanger: true,
                                }
                            });
                        })
                }}>
                    <FormGroup controlId="tweetAdder">
                        <FormLabel>Add tweet. {256 - this.state.tweetText.length} characters left</FormLabel>
                        <Form.Control as="textarea" rows="3" ref={tweetRef} placeholder="max 256 characters"
                                      value={this.state.tweetText}
                                      onChange={() => this.setState(() => {
                                          if (tweetRef.current.value.length <= 256) {
                                              return {tweetText: tweetRef.current.value}
                                          }
                                      })}/>
                    </FormGroup>
                    <Button variant="primary" type="submit">
                        Add tweet
                    </Button>

                </Form>
            </div>
        );
    }
}