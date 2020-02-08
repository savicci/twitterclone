import React, {Component} from "react";
import {getTweets} from "../utils/APIUtils";
import {Jumbotron} from "react-bootstrap";
import styled from "styled-components";

const MainPageWrapper = styled.div`
{
    display: flex;
    flex-direction: column;
    padding: 16px;
    justify-content: center;
    overflow: auto;
}
`;

export default class MainPage extends Component {

    constructor(props) {
        super(props);
        this.state = {
            tweets: []
        };

        getTweets({
            username: this.props.user.username
        }).then(response => {
            this.setState(() => {
                return {
                    tweets: response,
                }
            })
        })
    }

    componentDidMount() {
        this.interval = setInterval(() => {
            getTweets({
                username: this.props.user.username
            }).then(response => {
                this.setState(() => {
                    return {
                        tweets: response,
                    }
                })
            })
        }, 10000);
    }

    componentWillUnmount() {
        clearInterval(this.interval);
    }

    render() {
        const tweets = this.state.tweets.map(tweet => {
            return <Tweet tweet={tweet}/>
        });

        return (
            <MainPageWrapper>
                {tweets}
            </MainPageWrapper>
        )
    }
}

const Wrapper = styled.div`
{
    display: flex;
    justify-content: space-between;
}
`;

class Tweet extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <Jumbotron>
                <Wrapper>
                    <div>
                        User
                        <h4>{this.props.tweet.owner}</h4>
                    </div>
                    <div>
                        Data stworzenia
                        <h4>{this.props.tweet.createdAt}</h4>
                    </div>
                </Wrapper>
                <div style={{display: "flex", justifyContent: "center"}}>
                    <p>{this.props.tweet.text}</p>
                </div>
            </Jumbotron>
        );
    }
}