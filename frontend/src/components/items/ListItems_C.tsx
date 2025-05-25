import React from "react";
import { Box, Typography, Card, CardContent } from "@mui/material";
import Image from "next/image";

interface ListItemProps {
  title: string;
  description: string;
  primaryButton?: {
    label: string;
    onClick: () => void;
  };
  secondaryButton?: {
    label: string;
    onClick: () => void;
  };
  image?: string;
}

const ListItems_C: React.FC<ListItemProps> = ({
  title,
  description,
  image,
}) => {
  return (
    <Card className="w-full max-w-2xl mx-auto my-4 shadow-md hover:shadow-lg transition-shadow">
      <CardContent className="flex gap-4">
        {image && (
          <Box className="w-24 h-24 shrink-0">
            <Image
              src={image}
              alt={title}
              className="rounded-lg"
              layout="fill"
              objectFit="cover"
            />
          </Box>
        )}
        <Box className="flex-1">
          <Typography variant="h6" className="font-semibold mb-2">
            {title}
          </Typography>
          <Typography variant="body2" className="text-gray-600 mb-4">
            {description}
          </Typography>
          {/* <Box className="flex gap-2">
            {primaryButton && (
              <Button
                variant="contained"
                onClick={primaryButton.onClick}
                className="bg-blue-600 hover:bg-blue-700"
              >
                {primaryButton.label}
              </Button>
            )}
            {secondaryButton && (
              <Button
                variant="outlined"
                onClick={secondaryButton.onClick}
                className="border-blue-600 text-blue-600 hover:bg-blue-50"
              >
                {secondaryButton.label}
              </Button>
            )}
          </Box> */}
        </Box>
      </CardContent>
    </Card>
  );
};

export default ListItems_C;
