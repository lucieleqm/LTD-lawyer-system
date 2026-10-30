import { Fieldset, FieldsetProps } from '@mantine/core';
import classes from './FormSection.module.css';

interface FormSectionProps extends FieldsetProps {
  legend: string;
  children: React.ReactNode;
}
export function FormSection({ legend, children, ...rest }: FormSectionProps) {
  return (
    <Fieldset
      legend={legend}
      classNames={{
        root: classes.root,
        legend: classes.legend,
      }}
      {...rest}
    >
      {children}
    </Fieldset>
  );
}
