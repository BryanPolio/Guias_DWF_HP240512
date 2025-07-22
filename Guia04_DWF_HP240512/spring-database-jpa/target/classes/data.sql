INSERT INTO POST (
    ID,
    TITLE,
    POST_DATE
)
VALUES (
	1,
    'SpringBoot as a back-end',
    '2023-09-29'
);

INSERT INTO POST_COMMENT (
    ID,
    REVIEW,
    COMMENT_DATE,
    POST_ID
)
VALUES (
	1,
    'Great post',
    '2023-10-05',
    1
);